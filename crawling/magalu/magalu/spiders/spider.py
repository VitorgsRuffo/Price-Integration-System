from ast import Yield
from cmath import inf
import scrapy
import time
''
class MagaluSpider(scrapy.Spider):
    name = "magalu"
    
    headers = {
        "Accept": "test/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
        "Accept-Encoding": "br,gzip,deflate", 
        "Accept-Language": "en-gb", 
        "Referer": "http://www.google.com/",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Safari/605.1.15"
    }

    def start_requests(self):
        url = 'https://www.magazineluiza.com.br/iphone/celulares-e-smartphones/s/te/teip?page=1'
        yield scrapy.Request(url=url,  headers=self.headers, callback=self.parse_home_page)


    def parse_home_page(self, response):

        current_page = response.css('.css-1a9p55p.css-197gxuo a::text').get()

        # Salvando as informacoes da loja somente uma vez (Quando estiver na pagina 1)
        if current_page == '1':
            store_infos = self.parse_store(response)
            yield {
                'loja' : {
                    'nome': store_infos['nome_loja'],
                    'endereco': store_infos['endereco'],
                    'telefone': store_infos['telefone'],
                    'nome_logo': store_infos['nome_logo']    
                }
            }
        
        for magalu in response.css('#showcase ul a::attr(href)'):
            yield scrapy.Request(magalu.get(), callback=self.parse_iphone_page)

        next_page = response.css('.css-1a9p55p.css-197gxuo + .css-1a9p55p a::text').get()
        if next_page is not None:
            next_page = response.url[:-1] + next_page
            yield scrapy.Request(next_page, callback=self.parse_home_page)
    

    def parse_store(self, response):
        store_infos = {}

        store_infos['nome_loja'] = response.css('.container-left-top-header a::text').get()
        store_infos['endereco'] = response.css('.bg-footer-address::text').get()
        store_infos['telefone'] = response.css('.phone-buyphone::text').get()
        store_infos['nome_logo'] = (store_infos['nome_loja']).replace(" ","").lower() + ".jpg"

        return store_infos

        
    def parse_iphone_page(self, response):
        iphone = self.parse_iphone(response)
        ratings = self.parse_ratings(response)

        yield {
            'iphone' : iphone,
            'avaliacoes': ratings
        }
    
    
    def parse_iphone(self, response):
        iphone_infos = {}
        
        try:
            iphone_infos['preco_aprazo'] = (response.css('.price-template::text')[2].get())
        except:
            iphone_infos['preco_aprazo'] = (response.css('.price-template::text')[1].get())
        
        iphone_infos['cor'] = self.parseTable(response, "Cor")
        iphone_infos['tam_tela'] = self.parseTable(response, "Tamanho da tela")
        iphone_infos['resolucao_cam_front'] = self.parseTable(response, "Resolução da câmera frontal")
        iphone_infos['resolucao_cam_tras'] = self.parseTable(response, "Resolução da câmera traseira")
        iphone_infos['mem_int'] = self.parseTable(response, "Memória interna")

        iphone = {
            'cod': response.css('.header-product__code::text').get(),
            'nome_loja': response.css('.container-left-top-header a::text').get(),
            'link_iphone': response.url,
            'link_imagem': response.css('.showcase-product__container-img a img::attr(src)').get(),
            'titulo': response.css('.header-product__title::text').get(),
            'preco_avista': response.css('.price-template__text::text').get(),
            'preco_aprazo': iphone_infos['preco_aprazo'],
            'cor': iphone_infos['cor'],
            'tam_tela': iphone_infos['tam_tela'],
            'resolucao_cam_front': iphone_infos['resolucao_cam_front'],
            'resolucao_cam_tras': iphone_infos['resolucao_cam_tras'],
            'mem_int': iphone_infos['mem_int']
        }

        return iphone


    def parse_ratings(self, response):
        ratings = []

        for rating in response.css('.wrapper-review__comment'):
            
            avaliacao = {
                'titulo' : rating.css('.product-review__text-content--title::text').get(),
                'descricao' : rating.css('.product-review__post .product-review__text-content::text').get(),
                'data' : 'None',
                'avaliador_nome' : rating.css('.product-review__text-box .product-review__text-content::text').get(),
                'likes' : self.get_likes_deslikes(rating.css('.product-review__text-highlight.product-review__spacing::text')[0].getall()),
                'deslikes' : self.get_likes_deslikes(rating.css('.product-review__text-highlight.product-review__spacing::text')[1].getall()),
                'nota' : self.get_nota(rating.css('.rating-percent__small-star.product-review__post-stars .rating-percent__numbers::text').get()),
                'iphone_cod': response.css('.header-product__code::text').get(),
                'nome_loja' : response.css('.container-left-top-header a::text').get()
            }
            
            ratings.append(avaliacao)
            
        return ratings


    def parseTable(self, response, key):
        information = None
        table = response.css('.description__container-text table')

        # No site há tres estruturas diferentes de tabelas, sendo assim é feito o tratamento para cada uma delas

        # table 1
        for line in table:
            try:
                if line.css('.description__information-left::text').get().strip().lower() == key.lower() :
                    information = line.css('.description__information-box-right::text').get().strip()
                    break
            except:
                continue
            
        # table 2
        if information is None:
            for line in table:
                try:
                    if line.css('.description__information-box-left::text').get().strip().lower() == key.lower():
                        information = line.css('.description__information-box-right::text').get().strip()
                        break
                except:
                    continue
        
        # table 3
        if information is None:
            table = response.css('.description__container-text.description__box')
            for line in table:
                try:
                    if line.css('.description__information-box-left::text').get().strip().lower() == key.lower():
                        information = line.css('.description__information-box-right::text').get().strip()
                        break
                except:
                    continue
        
        return information


    def get_likes_deslikes(self, like_crawled):
        like_crawled = like_crawled[0]
        like_crawled = like_crawled.replace('(','')
        like_crawled = like_crawled.replace(')','')
        return like_crawled[0]


    def get_nota(self, nota_crawled):
        nota_crawled = nota_crawled.replace('(','')
        nota_crawled = nota_crawled.replace(')','')
        nota_crawled = int(int(nota_crawled)/20)
        return nota_crawled
                