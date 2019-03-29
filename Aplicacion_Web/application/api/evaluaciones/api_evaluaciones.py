import web
import config
import json


class Api_evaluaciones:
    def get(self, id_evaluacion):
        try:
            # http://0.0.0.0:8080/api_evaluaciones?user_hash=12345&action=get
            if id_evaluacion is None:
                result = config.model.get_all_evaluaciones()
                evaluaciones_json = []
                for row in result:
                    tmp = dict(row)
                    evaluaciones_json.append(tmp)
                web.header('Content-Type', 'application/json')
                return json.dumps(evaluaciones_json)
            else:
                # http://0.0.0.0:8080/api_evaluaciones?user_hash=12345&action=get&id_evaluacion=1
                result = config.model.get_evaluaciones(int(id_evaluacion))
                evaluaciones_json = []
                evaluaciones_json.append(dict(result))
                web.header('Content-Type', 'application/json')
                return json.dumps(evaluaciones_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            evaluaciones_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(evaluaciones_json)

# http://0.0.0.0:8080/api_evaluaciones?user_hash=12345&action=put&id_evaluacion=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=0
    def put(self, calificacion,id_recomendacion,nombre_usuario):
        try:
            config.model.insert_evaluaciones(calificacion,id_recomendacion,nombre_usuario)
            evaluaciones_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(evaluaciones_json)
        except Exception as e:
            print "PUT Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_evaluaciones?user_hash=12345&action=delete&id_evaluacion=1
    def delete(self, id_evaluacion):
        try:
            config.model.delete_evaluaciones(id_evaluacion)
            evaluaciones_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(evaluaciones_json)
        except Exception as e:
            print "DELETE Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_evaluaciones?user_hash=12345&action=update&id_evaluacion=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=default.jpg
    def update(self, id_evaluacion, calificacion,id_recomendacion,nombre_usuario):
        try:
            config.model.edit_evaluaciones(id_evaluacion,calificacion,id_recomendacion,nombre_usuario)
            evaluaciones_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(evaluaciones_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            evaluaciones_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(evaluaciones_json)

    def GET(self):
        user_data = web.input(
            user_hash=None,
            action=None,
            id_evaluacion=None,
            calificacion=None,
            id_recomendacion=None,
            nombre_usuario=None,
        )
        try:
            user_hash = user_data.user_hash  # user validation
            action = user_data.action  # action GET, PUT, DELETE, UPDATE
            id_evaluacion=user_data.id_evaluacion
            calificacion=user_data.calificacion
            id_recomendacion=user_data.id_recomendacion
            nombre_usuario=user_data.nombre_usuario
            # user_hash
            if user_hash == 'dc243fdf1a24cbced74db81708b30788':
                if action is None:
                    raise web.seeother('/404')
                elif action == 'get':
                    return self.get(id_evaluacion)
                elif action == 'put':
                    return self.put(calificacion,id_recomendacion,nombre_usuario)
                elif action == 'delete':
                    return self.delete(id_evaluacion)
                elif action == 'update':
                    return self.update(id_evaluacion, calificacion,id_recomendacion,nombre_usuario)
            else:
                raise web.seeother('/404')
        except Exception as e:
            print "WEBSERVICE Error {}".format(e.args)
            raise web.seeother('/404')
