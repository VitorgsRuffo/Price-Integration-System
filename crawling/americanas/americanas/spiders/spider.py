import scrapy
import time
from datetime import date
import re


class AmericanasSpider(scrapy.Spider):
    name = "americanas"
    headers = {
        "Accept": "test/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
        "Accept-Encoding": "br,gzip,deflate", 
        "Accept-Language": "en-gb", 
        "Referer": "http://www.google.com/",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Safari/605.1.15"
    }
    maximum_iphones_to_process = 20

    def start_requests(self):
        url = 'https://www.americanas.com.br/categoria/celulares-e-smartphones/smartphone/iphone/f/sistema-operacional-iphone%20ios/g/condicao-novo?limit=200&offset=0'  
        yield scrapy.Request(url=url, headers=self.headers, callback=self.parse_home_page)


    def parse_home_page(self, response):
        yield {
            'loja':'americanas',
            'data': str(date.today())
        }

        processed_iphones = 0
        #another way of selecting iphone_divs: response.css('main div div.middle-area__WrapperRight-sc-1k81b14-0 div:nth-child(3) div div a')
        iphone_divs = response.css('main div div.middle-area__WrapperRight-sc-1k81b14-0 div.product-grid-new-list__GridItem-sc-1suhvr9-1 div div.src__Wrapper-sc-1wgxjb2-0')
        
        time.sleep(2)

        for iphone_div in iphone_divs:
            if iphone_div.css('a.outOfStockCardList__Wrapper-sc-1ghgij6-0'): #o iphone nao se encontra no estoque, e, portanto, nao sera parseado.
                continue
            
            iphone_rel_link = iphone_div.css('a::attr(href)').get()
            iphone_abs_link = response.urljoin(iphone_rel_link)
            preco_avista, preco_aprazo = iphone_div.css('div.price-info__ContainerPriceInstalmentCash-sc-1td1088-2 span')
            preco_avista = preco_avista.css('::text').get()
            preco_aprazo = preco_aprazo.css('::text').get()

            time.sleep(2)

            yield scrapy.Request(iphone_abs_link, headers=self.headers, callback=self.parse_iphone_page, cb_kwargs=dict(preco_avista=preco_avista, preco_aprazo=preco_aprazo))
            
            processed_iphones += 1
            if processed_iphones >= self.maximum_iphones_to_process:
                break


    def parse_iphone_page(self, response, preco_avista, preco_aprazo):
        iphone = self.parse_iphone(response, preco_avista, preco_aprazo)
        if iphone is None:
            return
        ratings = self.parse_ratings(response)
        yield {
            'iphone': iphone,
            'avaliacoes': ratings
        }


    def parse_iphone(self, response, preco_avista, preco_aprazo):
        table_attributes_mapping = {
            'Código': 'modelo-cod',
            'Tamanho do Display': 'tam_tela',
            'Câmera Frontal': 'resolucao_cam_front',
            'Câmera Traseira': 'resolucao_cam_tras',
            'Memória Interna': 'mem_int',
            'Memória RAM': 'mem_ram'
        }

        titulo = response.css('.product-title__Title-sc-1hlrxcw-0.jyetLr::text').get()
        titulo = titulo.lower()
        modelo_nome = re.search("iphone ..? (mini|pro max|pro|max|plus)?", titulo)
        if(modelo_nome is None):
            return None
        modelo_nome = modelo_nome.group()

        iphone = {
            'mem_int': '', 
            'modelo-nome' : modelo_nome,
            'modelo-cod': '',
            'link_iphone': response.url, 
            'link_imagem': response.css('.main-image__Container-sc-1i1hq2n-1.iCNHlx div picture img::attr(src)').get(), 
            'tam_tela': '', 
            'resolucao_cam_front': '', 
            'resolucao_cam_tras': '', 
            'mem_ram': '', 
            'titulo': titulo,
            'preco_avista': preco_avista, 
            'preco_aprazo': preco_aprazo, 
            'media_nota': response.css('.header__RatingValue-sc-ibr017-9::text').get(),
            'quantidade_avaliacoes': response.css('.header__ReviewsValue-sc-ibr017-8::text').getall()[1]
        }

        attributes_table = response.css('table.src__SpecsCell-sc-70o4ee-5.gYhGqJ tbody tr')
        for row in attributes_table:
            attribute_name, attribute_value = row.css('td::text').getall()
            try:
                mapped_attribute_name = table_attributes_mapping[attribute_name]
            except:
                continue
            iphone[mapped_attribute_name] = attribute_value
        
        return iphone


    def parse_ratings(self, response):
        rating_divs = response.css('.review__Wrapper-sc-18mpb23-1')
        ratings_amount = len(rating_divs)
        ratings = [dict() for j in range(0, ratings_amount)]
        i = 0
        for rating_div in rating_divs:
            rating = rating_div.css('div.review__WrapperReview-sc-18mpb23-2')
            ratings[i]['titulo'] = rating.css('h4::text').get()
            ratings[i]['descricao'] = rating.css('span::text').get()
            rating_meta = rating.css('div.review__User-sc-18mpb23-5::text').getall()
            ratings[i]['data'] = rating_meta[-1] 
            if rating_meta[0] != ' em ':
                ratings[i]['avaliador_nome'] = rating_meta[0]
            else:
                ratings[i]['avaliador_nome'] = ' '

            #Atributos que ainda não foram possiveis de serem selecionados...
            #likes_deslikes = rating_div.css('button.review__ActionRecommentation-sc-18mpb23-9')
            #likes_deslikes = likes_deslikes.css('span::text').getall()
            #ratings[i]['likes'] = likes_deslikes[1] #vem sempre zerado, porque?
            #ratings[i]['deslikes'] = likes_deslikes[4]  #vem sempre zerado, porque?
            #ratings[i]['nota'] = ' ' #Ainda nao foi encontrado um jeito de selecionar a nota...
            i += 1
        return ratings
