import scrapy

class MagaluSpider(scrapy.Spider):
    name = "magalu"

    def start_requests(self):
        url = 'https://www.magazineluiza.com.br/iphone/celulares-e-smartphones/s/te/teip?page=1'
        yield scrapy.Request(url=url, callback=self.parse_home_page)


    def parse_home_page(self, response):

        for magalu in response.css('#showcase ul a::attr(href)'):
            yield {
                'link': magalu.get()
            }

        next_page = response.css('.css-1a9p55p.css-197gxuo + .css-1a9p55p a::text').get()
        if next_page is not None:
            next_page = response.url[:-1] + next_page
            yield scrapy.Request(next_page, callback=self.parse_home_page)