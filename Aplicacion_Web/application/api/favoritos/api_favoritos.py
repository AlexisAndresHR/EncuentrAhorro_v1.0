import web
import config
import json


class Api_favoritos:
    def get(self, id_favorito):
        try:
            # http://0.0.0.0:8080/api_favoritos?user_hash=12345&action=get
            if id_favorito is None:
                result = config.model.get_all_favoritos()
                favoritos_json = []
                for row in result:
                    tmp = dict(row)
                    favoritos_json.append(tmp)
                web.header('Content-Type', 'application/json')
                return json.dumps(favoritos_json)
            else:
                # http://0.0.0.0:8080/api_favoritos?user_hash=12345&action=get&id_favorito=1
                result = config.model.get_favoritos(int(id_favorito))
                favoritos_json = []
                favoritos_json.append(dict(result))
                web.header('Content-Type', 'application/json')
                return json.dumps(favoritos_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            favoritos_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(favoritos_json)

# http://0.0.0.0:8080/api_favoritos?user_hash=12345&action=put&id_favorito=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=0
    def put(self, nombre_usuario,id_recomendacion):
        try:
            config.model.insert_favoritos(nombre_usuario,id_recomendacion)
            favoritos_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(favoritos_json)
        except Exception as e:
            print "PUT Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_favoritos?user_hash=12345&action=delete&id_favorito=1
    def delete(self, id_favorito):
        try:
            config.model.delete_favoritos(id_favorito)
            favoritos_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(favoritos_json)
        except Exception as e:
            print "DELETE Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_favoritos?user_hash=12345&action=update&id_favorito=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=default.jpg
    def update(self, id_favorito, nombre_usuario,id_recomendacion):
        try:
            config.model.edit_favoritos(id_favorito,nombre_usuario,id_recomendacion)
            favoritos_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(favoritos_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            favoritos_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(favoritos_json)

    def GET(self):
        user_data = web.input(
            user_hash=None,
            action=None,
            id_favorito=None,
            nombre_usuario=None,
            id_recomendacion=None,
        )
        try:
            user_hash = user_data.user_hash  # user validation
            action = user_data.action  # action GET, PUT, DELETE, UPDATE
            id_favorito=user_data.id_favorito
            nombre_usuario=user_data.nombre_usuario
            id_recomendacion=user_data.id_recomendacion
            # user_hash
            if user_hash == 'dc243fdf1a24cbced74db81708b30788':
                if action is None:
                    raise web.seeother('/404')
                elif action == 'get':
                    return self.get(id_favorito)
                elif action == 'put':
                    return self.put(nombre_usuario,id_recomendacion)
                elif action == 'delete':
                    return self.delete(id_favorito)
                elif action == 'update':
                    return self.update(id_favorito, nombre_usuario,id_recomendacion)
            else:
                raise web.seeother('/404')
        except Exception as e:
            print "WEBSERVICE Error {}".format(e.args)
            raise web.seeother('/404')
