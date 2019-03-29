import web
import config
import json


class Api_recomendaciones:
    def get(self, id_recomendacion):
        try:
            # http://0.0.0.0:8080/api_recomendaciones?user_hash=12345&action=get
            if id_recomendacion is None:
                result = config.model.get_all_recomendaciones()
                recomendaciones_json = []
                for row in result:
                    tmp = dict(row)
                    recomendaciones_json.append(tmp)
                web.header('Content-Type', 'application/json')
                return json.dumps(recomendaciones_json)
            else:
                # http://0.0.0.0:8080/api_recomendaciones?user_hash=12345&action=get&id_recomendacion=1
                result = config.model.get_recomendaciones(int(id_recomendacion))
                recomendaciones_json = []
                recomendaciones_json.append(dict(result))
                web.header('Content-Type', 'application/json')
                return json.dumps(recomendaciones_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            recomendaciones_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(recomendaciones_json)

# http://0.0.0.0:8080/api_recomendaciones?user_hash=12345&action=put&id_recomendacion=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=0
    def put(self, fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa):
        try:
            config.model.insert_recomendaciones(fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa)
            recomendaciones_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(recomendaciones_json)
        except Exception as e:
            print "PUT Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_recomendaciones?user_hash=12345&action=delete&id_recomendacion=1
    def delete(self, id_recomendacion):
        try:
            config.model.delete_recomendaciones(id_recomendacion)
            recomendaciones_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(recomendaciones_json)
        except Exception as e:
            print "DELETE Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_recomendaciones?user_hash=12345&action=update&id_recomendacion=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=default.jpg
    def update(self, id_recomendacion, fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa):
        try:
            config.model.edit_recomendaciones(id_recomendacion,fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa)
            recomendaciones_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(recomendaciones_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            recomendaciones_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(recomendaciones_json)

    def GET(self):
        user_data = web.input(
            user_hash=None,
            action=None,
            id_recomendacion=None,
            fecha=None,
            descripcion=None,
            precio=None,
            latitud_ubi=None,
            longitud_ubi=None,
            duracion=None,
            id_categoria=None,
            id_producto=None,
            nombre_usuario=None,
            num_megusta=None,
            num_comentarios=None,
            promedio_evaluaciones=None,
            recomendacion_activa=None,
        )
        try:
            user_hash = user_data.user_hash  # user validation
            action = user_data.action  # action GET, PUT, DELETE, UPDATE
            id_recomendacion=user_data.id_recomendacion
            fecha=user_data.fecha
            descripcion=user_data.descripcion
            precio=user_data.precio
            latitud_ubi=user_data.latitud_ubi
            longitud_ubi=user_data.longitud_ubi
            duracion=user_data.duracion
            id_categoria=user_data.id_categoria
            id_producto=user_data.id_producto
            nombre_usuario=user_data.nombre_usuario
            num_megusta=user_data.num_megusta
            num_comentarios=user_data.num_comentarios
            promedio_evaluaciones=user_data.promedio_evaluaciones
            recomendacion_activa=user_data.recomendacion_activa
            # user_hash
            if user_hash == 'dc243fdf1a24cbced74db81708b30788':
                if action is None:
                    raise web.seeother('/404')
                elif action == 'get':
                    return self.get(id_recomendacion)
                elif action == 'put':
                    return self.put(fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa)
                elif action == 'delete':
                    return self.delete(id_recomendacion)
                elif action == 'update':
                    return self.update(id_recomendacion, fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa)
            else:
                raise web.seeother('/404')
        except Exception as e:
            print "WEBSERVICE Error {}".format(e.args)
            raise web.seeother('/404')
