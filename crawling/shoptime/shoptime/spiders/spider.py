import scrapy
import time
#to do:
#   - remover entidade duvida (DER, Relacional e SQL).


class ShoptimeSpider(scrapy.Spider):
    name = "shoptime"
    store_attributes = None
    headers = {
        "Accept": "test/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
        "Accept-Encoding": "br,gzip,deflate", 
        "Accept-Language": "en-gb", 
        "Referer": "http://www.google.com/",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36"
    }
    maximum_home_pages_to_process = 1
    
    def generate_ith_home_page_link(self, i):
        return f'https://www.shoptime.com.br/categoria/celulares-e-smartphones/smartphone/iphone/g/tipo-de-produto-Iphone/pagina-{i}?ordenacao=relevance&origem=blanca'

    def start_requests(self):
        url = self.generate_ith_home_page_link(1)
        yield scrapy.Request(url=url, headers=self.headers, callback=self.parse_home_page, cb_kwargs=dict(ith_page=1))
    

    def parse_home_page(self, response, ith_page):
        iphone_divs = response.css('.product-grid-item.ProductGrid__GridColumn-sc-49j2r8-0 div div a.Link-bwhjk3-2')
        if not iphone_divs:
            return 
        if ith_page == 1:
            self.parse_store(response)
            yield {
                'nome': self.store_attributes[0],
                'endereco': self.store_attributes[1],
                'telefone': self.store_attributes[2],
                'nome_logo': self.store_attributes[3]
            }

        time.sleep(2)
        for iphone_div in iphone_divs:
            if iphone_div.css('.UnavailableMessage-bwhjk3-16'): #o iphone nao se encontra no estoque, e, portanto, nao sera parseado.
                continue
            
            iphone_rel_link = iphone_div.css('::attr(href)').get()
            iphone_abs_link = response.urljoin(iphone_rel_link)
            precos = iphone_div.css('section div.Info-bwhjk3-5 span::text').getall()
            preco_avista = precos[-2]
            preco_aprazo = precos[-1]

            time.sleep(2)
            yield scrapy.Request(iphone_abs_link, headers=self.headers, callback=self.parse_iphone_page, cb_kwargs=dict(preco_avista=preco_avista, preco_aprazo=preco_aprazo))
            
        
        if ith_page < self.maximum_home_pages_to_process:
            next_page_link = self.generate_ith_home_page_link(ith_page+1)
            yield scrapy.Request(next_page_link, headers=self.headers, callback=self.parse_home_page, cb_kwargs=dict(ith_page=ith_page+1))


    def parse_store(self, response):
        info_string = response.css('address::text').get()
        info = info_string.split(' / ')
        phone = "4003-9898"
        self.store_attributes =  ["shoptime", info[3], phone, 'shoptime.png']


    def parse_iphone_page(self, response, preco_avista, preco_aprazo):
        iphone = self.parse_iphone(response, preco_avista, preco_aprazo)
        ratings = self.parse_ratings(response, iphone['cod'])
        #Não ha como selecionar as duvidas pois elas são renderizadas no client-side por algum script .js...
        #doubts = self.parse_doubts(response, iphone['cod'])
        yield {
            'iphone': iphone,
            'avaliacoes': ratings
            #'duvidas': doubts
        }


    def parse_iphone(self, response, preco_avista, preco_aprazo):
        table_attributes_mapping = {
            'Código': 'cod',
            'Cor': 'cor',
            'Tamanho do Display': 'tam_tela',
            'Câmera Frontal': 'resolucao_cam_front',
            'Câmera Traseira': 'resolucao_cam_tras',
            'Memória Interna': 'mem_int',
            'Memória RAM': 'mem_ram'
        }

        iphone = {
            'cod': '',
            'loja_nome': self.store_attributes[0],
            'link_iphone': response.url, 
            'link_imagem': response.css('.src__Container-sc-1a23x5b-3 picture img::attr(src)').get(), 
            'titulo': response.css('.src__Title-sc-79cth1-0::text').get(), 
            'cor': '', 
            'preco_avista': preco_avista, 
            'preco_aprazo': preco_aprazo, 
            'tam_tela': '', 
            'resolucao_cam_front': '', 
            'resolucao_cam_tras': '', 
            'mem_int': '', 
            'mem_ram': '', 
        }

        attributes_table = response.css('table tbody tr')
        for row in attributes_table:
            attribute_name, attribute_value = row.css('td::text').getall()
            try:
                mapped_attribute_name = table_attributes_mapping[attribute_name]
            except:
                continue
            iphone[mapped_attribute_name] = attribute_value
        
        return iphone


    def parse_ratings(self, response, iphone_cod):
        rating_divs = response.css('.review__Wrapper-l45my2-1')
        ratings_amount = len(rating_divs)
        ratings = [dict() for j in range(0, ratings_amount)]
        i = 0
        for rating_div in rating_divs:
            rating = rating_div.css('.review__WrapperReview-l45my2-2') 
            ratings[i]['titulo'] = rating.css('h4::text').get()
            ratings[i]['descricao'] = rating.css('span::text').get()
            rating_meta = rating.css('div.review__User-l45my2-5::text').getall()
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

            ratings[i]['iphone_cod'] = iphone_cod
            ratings[i]['loja_nome'] = self.store_attributes[0]
            i += 1
        return ratings
