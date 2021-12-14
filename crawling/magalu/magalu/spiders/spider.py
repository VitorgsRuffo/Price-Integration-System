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
            yield scrapy.Request(next_page, callback=self.parse_home_page)



    def parse_iphone_page(self, response):
        store_infos = self.parse_store(response)
        #iphone_infos = parse_iphone()
        #duvidas_infos = parse_duvidas()
        #rating_infos = parse_ratings()
        

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

        return infos