import web
import config
import json


class Api_categorias_productos:
    def get(self, id_categoria):
        try:
            # http://0.0.0.0:8080/api_categorias_productos?user_hash=12345&action=get
            if id_categoria is None:
                result = config.model.get_all_categorias_productos()
                categorias_productos_json = []
                for row in result:
                    tmp = dict(row)
                    categorias_productos_json.append(tmp)
                web.header('Content-Type', 'application/json')
                return json.dumps(categorias_productos_json)
            else:
                # http://0.0.0.0:8080/api_categorias_productos?user_hash=12345&action=get&id_categoria=1
                result = config.model.get_categorias_productos(int(id_categoria))
                categorias_productos_json = []
                categorias_productos_json.append(dict(result))
                web.header('Content-Type', 'application/json')
                return json.dumps(categorias_productos_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            categorias_productos_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(categorias_productos_json)

# http://0.0.0.0:8080/api_categorias_productos?user_hash=12345&action=put&id_categoria=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=0
    def put(self, nombre_categoria):
        try:
            config.model.insert_categorias_productos(nombre_categoria)
            categorias_productos_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(categorias_productos_json)
        except Exception as e:
            print "PUT Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_categorias_productos?user_hash=12345&action=delete&id_categoria=1
    def delete(self, id_categoria):
        try:
            config.model.delete_categorias_productos(id_categoria)
            categorias_productos_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(categorias_productos_json)
        except Exception as e:
            print "DELETE Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_categorias_productos?user_hash=12345&action=update&id_categoria=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=default.jpg
    def update(self, id_categoria, nombre_categoria):
        try:
            config.model.edit_categorias_productos(id_categoria,nombre_categoria)
            categorias_productos_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(categorias_productos_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            categorias_productos_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(categorias_productos_json)

    def GET(self):
        user_data = web.input(
            user_hash=None,
            action=None,
            id_categoria=None,
            nombre_categoria=None,
        )
        try:
            user_hash = user_data.user_hash  # user validation
            action = user_data.action  # action GET, PUT, DELETE, UPDATE
            id_categoria=user_data.id_categoria
            nombre_categoria=user_data.nombre_categoria
            # user_hash
            if user_hash == 'dc243fdf1a24cbced74db81708b30788':
                if action is None:
                    raise web.seeother('/404')
                elif action == 'get':
                    return self.get(id_categoria)
                elif action == 'put':
                    return self.put(nombre_categoria)
                elif action == 'delete':
                    return self.delete(id_categoria)
                elif action == 'update':
                    return self.update(id_categoria, nombre_categoria)
            else:
                raise web.seeother('/404')
        except Exception as e:
            print "WEBSERVICE Error {}".format(e.args)
            raise web.seeother('/404')
