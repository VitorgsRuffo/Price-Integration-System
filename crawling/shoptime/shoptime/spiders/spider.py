import scrapy
import time
from datetime import date
import re


class ShoptimeSpider(scrapy.Spider):
    name = "shoptime"
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
            yield {
                'loja': 'shoptime',
                'data': str(date.today())
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
            'Código': 'modelo_cod',
            'Tamanho do Display': 'tam_tela',
            'Câmera Frontal': 'resolucao_cam_front',
            'Câmera Traseira': 'resolucao_cam_tras',
            'Memória Interna': 'mem_int',
            'Memória RAM': 'mem_ram'
        }

        titulo = response.css('.src__Title-sc-79cth1-0::text').get()
        titulo = titulo.lower()

        modelo_nome = re.search("iphone ..?( mini| pro max| pro| max| plus)?", titulo)
        if(modelo_nome is None):
            return None
        modelo_nome = modelo_nome.group()

        cor = re.search("red|vermelho|meia-noite|azul|prata|amarelo|branco|coral|roxo|cinza|cinza espacial|verde|grafite|estelar|azul-pacífico|rosa|rose gold|prateado|ouro|ouro rosa|preto|dourado|azul-sierra|azul sierra", titulo)
        if(cor is None):
            return None
        cor = cor.group()
        if cor == "red":
            cor = "vermelho"

        try:
            q_avaliacao = response.css('.header__ReviewsValue-sc-1o3gjvp-8::text').getall()[1]
        except:
            q_avaliacao = 0

        iphone = {
            'cor': cor,
            'mem_int': '', 
            'modelo_nome' : modelo_nome,
            'modelo_cod': '',
            'link_iphone': response.url, 
            'link_imagem': response.css('.src__Container-sc-1a23x5b-3 picture img::attr(src)').get(),
            'tam_tela': '', 
            'resolucao_cam_front': '', 
            'resolucao_cam_tras': '', 
            'mem_ram': '', 
            'titulo': response.css('.src__Title-sc-79cth1-0::text').get(), 
            'preco_avista': preco_avista, 
            'preco_aprazo': preco_aprazo, 
            'media_nota': response.css('.header__RatingValue-sc-1o3gjvp-9::text').get(),
            'quantidade_avaliacoes': q_avaliacao
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


    def parse_ratings(self, response):
        rating_divs = response.css('.review__WrapperReview-sc-l45my2-2')
        ratings_amount = len(rating_divs)
        ratings = [dict() for j in range(0, ratings_amount)]
        i = 0
        for rating_div in rating_divs:
            titulo = rating_div.css('h4::text').get()
            descricao = rating_div.css('span::text').get()
            if titulo is None and descricao is None:
                ratings.pop(i)
                continue
            ratings[i]['titulo'] = titulo
            ratings[i]['descricao'] = descricao
            rating_meta = rating_div.css('.review__User-sc-l45my2-5::text').getall()
            ratings[i]['data'] = rating_meta[-1] 
            if rating_meta[0] != ' em ':
                ratings[i]['avaliador_nome'] = rating_meta[0]
            else:
                ratings[i]['avaliador_nome'] = ' '

            i += 1
        return ratings
