import scrapy
import time
#to do:
#   - remover entidade duvida (DER, Relacional e SQL).
#   - shoptime...


class AmericanasSpider(scrapy.Spider):
    name = "americanas"
    store_attributes = None
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
        self.parse_store(response)
        yield {
            'nome': self.store_attributes[0],
            'endereco': self.store_attributes[1],
            'telefone': self.store_attributes[2],
            'nome_logo': self.store_attributes[3]
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


    def parse_store(self, response):
        info_string = response.css('address::text').get()
        info = info_string.split(' / ')
        #phone_string = response.css('.footer-item__Link-cgexy7-1.bwqVtN::text')[0].get()
        #phone = phone_string.split(' ')
        #phone = phone[1]
        phone = "4003-4848"
        self.store_attributes =  [info[0], info[3], phone, 'americanas.png']


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
            'link_imagem': response.css('.main-image__Container-sc-1i1hq2n-1.iCNHlx div picture img::attr(src)').get(), 
            'titulo': response.css('.product-title__Title-sc-1hlrxcw-0.jyetLr::text').get(), 
            'cor': '', 
            'preco_avista': preco_avista, 
            'preco_aprazo': preco_aprazo, 
            'tam_tela': '', 
            'resolucao_cam_front': '', 
            'resolucao_cam_tras': '', 
            'mem_int': '', 
            'mem_ram': '', 
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


    def parse_ratings(self, response, iphone_cod):
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

            ratings[i]['iphone_cod'] = iphone_cod
            ratings[i]['loja_nome'] = self.store_attributes[0]
            i += 1
        return ratings


    def parse_doubts(self, response, iphone_cod):
        doubt_divs = response.css('.question__Wrapper-p6e9ij-0')
        doubts_amount = len(doubt_divs)
        doubts = [dict() for j in range(0, doubts_amount)]
        i = 0
        for doubt_div in doubt_divs:
            doubt = doubt_div.css('div.question__QuestionAndAnswer-p6e9ij-1')
            doubts[i]['descricao'] = doubt.css('div div.question__QuestionText-p6e9ij-2 p::text').get()
            doubt_meta = doubt.css('div p.question__Owner-p6e9ij-3::text').getall()
            doubts[i]['data_duvida'] = doubt_meta[3] 
            doubts[i]['pessoa_nome'] = doubt_meta[1]
            doubts[i]['resposta'] = doubt.css('p.answer-box__Answer-y7vmri-1 span::text').get()
            doubts[i]['data_resposta'] = doubt.css('span.answer-box__AnswerCreatedAt-y7vmri-2::text').get()
            
            likes_deslikes = doubt_div.css('span.feedback__Count-sc-1hrd1kk-8::text').getall()
            doubts[i]['likes'] = likes_deslikes[1]
            doubts[i]['deslikes'] = likes_deslikes[4]

            doubts[i]['iphone_cod'] = iphone_cod
            doubts[i]['loja_nome'] = self.store_attributes[0]
            i += 1
        return doubts
