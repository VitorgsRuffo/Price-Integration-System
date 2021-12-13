import scrapy

class AmericanasSpider(scrapy.Spider):
    name = "americanas"
    store_attributes = None

    def start_requests(self):
        url = 'https://www.americanas.com.br/categoria/celulares-e-smartphones/smartphone/iphone/f/sistema-operacional-iphone%20ios/g/condicao-novo?limit=1000&offset=0'  
        headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36'}
        yield scrapy.Request(url=url, headers=headers, callback=self.parse_home_page)


    def parse_home_page(self, response):
        self.parse_store(response)
        yield {
            'nome': store_attributes[0],
            'endereco': store_attributes[1],
            'telefone': store_attributes[2],
            'nome_logo': store_attributes[3]
        }
        for iphone_rel_link in response.css('div div.src__Wrapper-sc-1k0ejj6-3.eflURh a::attr(href)').getall():
            iphone_abs_link = response.urljoin(iphone_rel_link)
            yield scrapy.Request(iphone_abs_link, callback=self.parse_iphone_page)


    def parse_iphone_page(self, response):
        iphone_attributes = self.parse_iphone(response)
        ratings = self.parse_ratings(response)
        doubts = self.parse_doubts(response)
        yield {
            'iphone': {
                'cod': iphone_attributes[0],
                'loja_nome': store_attributes[0],
                'link_iphone': iphone_attributes[1],
                'link_imagem': iphone_attributes[2],
                'titulo': iphone_attributes[3],
                'cor': iphone_attributes[4],
                'preco_avista': iphone_attributes[5],
                'preco_aprazo': iphone_attributes[6],
                'tam_tela': iphone_attributes[7],
                'resolucao_cam_front': iphone_attributes[8],
                'resolucao_cam_tras': iphone_attributes[9]
                'mem_int': iphone_attributes[10],
                'mem_ram': iphone_attributes[11]
            },
            'avaliacao': ratings,
            'duvida': doubts
        }

    # for quote in response.css('div.quote'):
    #     yield {
    #         'text': quote.css('span.text::text').get(),
    #         'author': quote.css('small.author::text').get(),
    #         'tags': quote.css('div.tags a.tag::text').getall(),
    #     }


    def parse_store(self, response):
        pass

    def parse_iphone(self, response):
        pass

    def parse_ratings(self, response):
        pass

    def parse_doubts(self, response):
        pass
    