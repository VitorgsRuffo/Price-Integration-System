import scrapy
import time

class AmericanasSpider(scrapy.Spider):
    name = "americanas"
    store_attributes = None

    def start_requests(self):
        url = 'https://www.americanas.com.br/categoria/celulares-e-smartphones/smartphone/iphone/f/sistema-operacional-iphone%20ios/g/condicao-novo?limit=10&offset=0'  
        headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36'}
        yield scrapy.Request(url=url, headers=headers, callback=self.parse_home_page)


    def parse_home_page(self, response):
        self.parse_store(response)
        yield {
            'nome': self.store_attributes[0],
            'endereco': self.store_attributes[1],
            'telefone': self.store_attributes[2],
            'nome_logo': self.store_attributes[3]
        }
        i = 0
        limit = 24
        for iphone_rel_link in response.css('div div.src__Wrapper-sc-1k0ejj6-3.eflURh a::attr(href)').getall():
            iphone_abs_link = response.urljoin(iphone_rel_link)
            if i == limit:
                time.sleep(3)
                i = 0
            yield scrapy.Request(iphone_abs_link, callback=self.parse_iphone_page)
            i+=1


    def parse_iphone_page(self, response):
        iphone = self.parse_iphone(response)
        if iphone is None: #se o iphone nao estiver disponivel no estoque não o salvamos...
            return
        ratings = self.parse_ratings(response)
        doubts = self.parse_doubts(response)
        yield {
            'iphone': iphone,
            'avaliacao': ratings,
            'duvida': doubts
        }


    def parse_store(self, response):
        info_string = response.css('.ft-address::text').get()
        info = info_string.split(' / ')
        phone_string = response.css('#list-level-2 li a::text').get()
        #phone = phone_string.split(' ')
        phone = "4003-4848"
        self.store_attributes =  [info[0], info[3], phone, 'americanas.png']


    def parse_iphone(self, response):
        price = response.css('.src__BestPrice-sc-1jvw02c-5.cBWOIB.priceSales::text').getall()[1]
        if not price:
            return None

        attributes_table = response.css('table.src__SpecsCell-sc-70o4ee-5.gYhGqJ tbody')
        iphone = {
            'cod': attributes_table.css('tr:nth-child(1) td:nth-child(2)::text').get(),
            'loja_nome': self.store_attributes[0],
            'link_iphone': response.url,
            'link_imagem': response.css('.main-image__Container-sc-1i1hq2n-1.iCNHlx div picture img::attr(src)').get(),
            'titulo': response.css('.product-title__Title-sc-1hlrxcw-0.jyetLr::text').get(),
            'cor': attributes_table.css('tr:nth-child(28) td:nth-child(2)::text').get(),
            'preco_avista': price,
            'preco_aprazo': ' ',
            'tam_tela': attributes_table.css('tr:nth-child(10) td:nth-child(2)::text').get(),
            'resolucao_cam_front': attributes_table.css('tr:nth-child(14) td:nth-child(2)::text').get(),
            'resolucao_cam_tras': attributes_table.css('tr:nth-child(13) td:nth-child(2)::text').get(),
            'mem_int': attributes_table.css('tr:nth-child(20) td:nth-child(2)::text').get(),
            'mem_ram': attributes_table.css('tr:nth-child(19) td:nth-child(2)::text').get()
        }
        return iphone


    def parse_ratings(self, response):
        pass


    def parse_doubts(self, response):
        pass
