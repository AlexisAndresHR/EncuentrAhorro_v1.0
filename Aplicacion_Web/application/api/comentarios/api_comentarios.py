import web
import config
import json


class Api_comentarios:
    def get(self, id_comentario):
        try:
            # http://0.0.0.0:8080/api_comentarios?user_hash=12345&action=get
            if id_comentario is None:
                result = config.model.get_all_comentarios()
                comentarios_json = []
                for row in result:
                    tmp = dict(row)
                    comentarios_json.append(tmp)
                web.header('Content-Type', 'application/json')
                return json.dumps(comentarios_json)
            else:
                # http://0.0.0.0:8080/api_comentarios?user_hash=12345&action=get&id_comentario=1
                result = config.model.get_comentarios(int(id_comentario))
                comentarios_json = []
                comentarios_json.append(dict(result))
                web.header('Content-Type', 'application/json')
                return json.dumps(comentarios_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            comentarios_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(comentarios_json)

# http://0.0.0.0:8080/api_comentarios?user_hash=12345&action=put&id_comentario=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=0
    def put(self, id_recomendacion,nombre_usuario,contenido,fecha_comentario):
        try:
            config.model.insert_comentarios(id_recomendacion,nombre_usuario,contenido,fecha_comentario)
            comentarios_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(comentarios_json)
        except Exception as e:
            print "PUT Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_comentarios?user_hash=12345&action=delete&id_comentario=1
    def delete(self, id_comentario):
        try:
            config.model.delete_comentarios(id_comentario)
            comentarios_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(comentarios_json)
        except Exception as e:
            print "DELETE Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_comentarios?user_hash=12345&action=update&id_comentario=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=default.jpg
    def update(self, id_comentario, id_recomendacion,nombre_usuario,contenido,fecha_comentario):
        try:
            config.model.edit_comentarios(id_comentario,id_recomendacion,nombre_usuario,contenido,fecha_comentario)
            comentarios_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(comentarios_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            comentarios_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(comentarios_json)

    def GET(self):
        user_data = web.input(
            user_hash=None,
            action=None,
            id_comentario=None,
            id_recomendacion=None,
            nombre_usuario=None,
            contenido=None,
            fecha_comentario=None,
        )
        try:
            user_hash = user_data.user_hash  # user validation
            action = user_data.action  # action GET, PUT, DELETE, UPDATE
            id_comentario=user_data.id_comentario
            id_recomendacion=user_data.id_recomendacion
            nombre_usuario=user_data.nombre_usuario
            contenido=user_data.contenido
            fecha_comentario=user_data.fecha_comentario
            # user_hash
            if user_hash == 'dc243fdf1a24cbced74db81708b30788':
                if action is None:
                    raise web.seeother('/404')
                elif action == 'get':
                    return self.get(id_comentario)
                elif action == 'put':
                    return self.put(id_recomendacion,nombre_usuario,contenido,fecha_comentario)
                elif action == 'delete':
                    return self.delete(id_comentario)
                elif action == 'update':
                    return self.update(id_comentario, id_recomendacion,nombre_usuario,contenido,fecha_comentario)
            else:
                raise web.seeother('/404')
        except Exception as e:
            print "WEBSERVICE Error {}".format(e.args)
            raise web.seeother('/404')
