import scrapy

class MagaluSpider(scrapy.Spider):
    name = "magalu"

    def start_requests(self):
        url = 'https://www.magazineluiza.com.br/iphone/celulares-e-smartphones/s/te/teip?page=1'
        yield scrapy.Request(url=url, callback=self.parse_home_page)


    def parse_home_page(self, response):

        for magalu in response.css('#showcase ul a::attr(href)'):
            yield scrapy.Request(magalu.get(), callback=self.parse_iphone_page)

        next_page = response.css('.css-1a9p55p.css-197gxuo + .css-1a9p55p a::text').get()
        if next_page is not None:
            next_page = response.url[:-1] + next_page
            #print(next_page+'\n\n')
            yield scrapy.Request(next_page, callback=self.parse_home_page)


    def parse_iphone_page(self, response):
        store_infos = self.parse_store(response)
        iphone_infos = self.parse_iphone(response)
        rating_infos = self.parse_ratings(response)

        yield {
            'loja' : {
                'nome': store_infos[0],
                'endereco': store_infos[1],
                'telefone': store_infos[2],
                'nome_logo': store_infos[3]
            },

            'iphone' : {
                'cod': iphone_infos[0],
                'nome_loja': iphone_infos[1],
                'link_iphone': iphone_infos[2],
                'link_imagem':iphone_infos[3],
                'titulo':iphone_infos[4],
                'preco_avista': iphone_infos[5],
                'preco_aprazo': iphone_infos[6],
                'cor': iphone_infos[7],
                'tam_tela': iphone_infos[8],
                'resolucao_cam_front': iphone_infos[9],
                'resolucao_cam_tras': iphone_infos[10],
                'mem_int': iphone_infos[11]
            }

        #    'avaliacao' : {
        #        'titulo' : rating_infos[0],
        #        'descricao' : rating_infos[1],
        #        'data' : rating_infos[2],
        #        'avaliador_nome' : rating_infos[3],
        #        'likes' : rating_infos[4],
        #        'deslikes' : rating_infos[5],
        #        'nota' : rating_infos[6],
        #        'iphone_cod ': rating_infos[7],
        #        'nome_loja' : rating_infos[8]
        #    }
        }
        

    def parse_store(self, response):
        infos = []

        # nome da loja
        infos.append(response.css('.container-left-top-header a::text').get())
        # Endereco
        infos.append(response.css('.bg-footer-address::text').get())
        #telefone
        infos.append(response.css('.phone-buyphone::text').get())
        #nome-logo
        infos.append(infos[0].replace(" ","").lower() + ".jpg")

        print(infos)
        return infos

    
    def parse_iphone(self, response):
        infos = []
        # Cod
        infos.append(response.css('.header-product__code::text').get())
        # nome_loja
        infos.append(response.css('.container-left-top-header a::text').get())
        # link_iphone
        infos.append(response.url)
        # link_imagem
        infos.append(response.css('.showcase-product__container-img a img::attr(src)').get())
        # titulo
        infos.append(response.css('.header-product__title::text').get())
        # preco_avista
        infos.append(response.css('.price-template__text::text').get())
        # preco_aprazo
        try:
            infos.append(response.css('.price-template::text')[2].get())
        except:
            infos.append(response.css('.price-template::text')[1].get())
        
        # tabels with informations
        tables = response.css('.description__container-text table')
        
        # cor
        infos.append(self.parseTable(tables, "Cor"))
        # tam_tela
        infos.append(infos.append(self.parseTable(tables, "Tamanho da tela")))
        # resolucao_cam_front
        infos.append(infos.append(self.parseTable(tables, "Resolução da câmera frontal")))
        # resolucao_cam_tras
        infos.append(infos.append(self.parseTable(tables, "Resolução da câmera traseira")))
        # mem_int
        infos.append(infos.append(self.parseTable(tables, "Memória interna")))

        print(infos)
        return infos


    def parse_ratings(self, response):
        infos = []

        # titulo
        infos.append(response.css('.product-review__text-content--title::text').get())
        # descricao
        infos.append(response.css('.product-review__post p::text').get())
        # data
        infos.append(response.css('.product-review__text-highlight::text').get())
        # avaliador_nome
        infos.append(response.css('.product-review__text-content::text').get())
        # likes
        #infos.append(response.css('.product-review__text-highlight.product-review__spacing::text').get())
        #deslikes
        #infos.append(response.css('.product-review__text-highlight.product-review__spacing::text').get())
        # nota
        #infos.append()
        # iphone_cod
        infos.append(response.css('.header-product__code::text').get())
        # loja_nome
        infos.append(response.css('.container-left-top-header a::text').get())

        print(infos)
        return infos


    def parseTable(self, tables, key):
        for t in tables:
            if t.css('.description__information-left::text').get() == key :
                return t.css('.description__information-box-right::text').get().strip()