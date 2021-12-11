import scrapy

class AmericanasSpider(scrapy.Spider):
    name = "americanas"

    def start_requests(self):
        url = 'https://www.americanas.com.br/categoria/celulares-e-smartphones/smartphone/iphone/f/sistema-operacional-iphone%20ios/g/condicao-novo?limit=24&offset=0'  
        yield scrapy.Request(url=url, callback=self.parse_home_page)


    def parse_home_page(self, response):
        #Alg:
        #1. Selecionar divs de produto da pagina.
        #2. Para cada div:
        #   2.1. extrair link.
        #   2.2. yieldar request para a pagina passando parse_iphone_page como callback.
        # 
        #3. Selecionar link da prox pagina.
        #4. yieldar request para a pagina passando parse_home_page como callback.





        # for quote in response.css('div.quote'):
        #     yield {
        #         'text': quote.css('span.text::text').get(),
        #         'author': quote.css('small.author::text').get(),
        #         'tags': quote.css('div.tags a.tag::text').getall(),
        #     }

        # next_page = response.css('li.next a').attrib['href']
        # if next_page is not None:
        #     next_page = response.urljoin(next_page)
        #     yield scrapy.Request(next_page, callback=self.parse)

    def parse_loja() : list

    def parse_iphone_page(self, response):
        #Alg:
        #1. Selecionar todas as infos necessarias e guardar em vars.
        # lista_loja = parse_loja()
        # lista_iphone = parse_iphone()
        # ...

        #2. yield {
        #   'loja' : {
        #       'nome': lista_loja[0],
        #        ...
        #   },
        
        #   'iphone': {
        #       'cod': lista_iphone[0],
        #        ...
        #   },
        #
        #   'avaliacao': {
        #   
        #   },
        #
        #   'duvida': {
        #       
        #   }
        # }
        #

