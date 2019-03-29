import web
import config
import json


class Api_usuarios:
    def get(self, nombre_usuario):
        try:
            # http://0.0.0.0:8080/api_usuarios?user_hash=12345&action=get
            if nombre_usuario is None:
                result = config.model.get_all_usuarios()
                usuarios_json = []
                for row in result:
                    tmp = dict(row)
                    usuarios_json.append(tmp)
                web.header('Content-Type', 'application/json')
                return json.dumps(usuarios_json)
            else:
                # http://0.0.0.0:8080/api_usuarios?user_hash=12345&action=get&nombre_usuario=1
                result = config.model.get_usuarios(int(nombre_usuario))
                usuarios_json = []
                usuarios_json.append(dict(result))
                web.header('Content-Type', 'application/json')
                return json.dumps(usuarios_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            usuarios_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(usuarios_json)

# http://0.0.0.0:8080/api_usuarios?user_hash=12345&action=put&nombre_usuario=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=0
    def put(self, email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario,codigo_recuperacion):
        try:
            config.model.insert_usuarios(email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario,codigo_recuperacion)
            usuarios_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(usuarios_json)
        except Exception as e:
            print "PUT Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_usuarios?user_hash=12345&action=delete&nombre_usuario=1
    def delete(self, nombre_usuario):
        try:
            config.model.delete_usuarios(nombre_usuario)
            usuarios_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(usuarios_json)
        except Exception as e:
            print "DELETE Error {}".format(e.args)
            return None

# http://0.0.0.0:8080/api_usuarios?user_hash=12345&action=update&nombre_usuario=1&product=nuevo&description=nueva&stock=10&purchase_price=1&price_sale=3&product_image=default.jpg
    def update(self, nombre_usuario, email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario,codigo_recuperacion):
        try:
            config.model.edit_usuarios(nombre_usuario,email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario,codigo_recuperacion)
            usuarios_json = '[{200}]'
            web.header('Content-Type', 'application/json')
            return json.dumps(usuarios_json)
        except Exception as e:
            print "GET Error {}".format(e.args)
            usuarios_json = '[]'
            web.header('Content-Type', 'application/json')
            return json.dumps(usuarios_json)

    def GET(self):
        user_data = web.input(
            user_hash=None,
            action=None,
            nombre_usuario=None,
            email_usuario=None,
            contrasena_usuario=None,
            nombre=None,
            apellido_pat=None,
            apellido_mat=None,
            fotografia_usuario=None,
            promedio_evaluaciones=None,
            nivel_usuario=None,
            codigo_recuperacion=None,
        )
        try:
            user_hash = user_data.user_hash  # user validation
            action = user_data.action  # action GET, PUT, DELETE, UPDATE
            nombre_usuario=user_data.nombre_usuario
            email_usuario=user_data.email_usuario
            contrasena_usuario=user_data.contrasena_usuario
            nombre=user_data.nombre
            apellido_pat=user_data.apellido_pat
            apellido_mat=user_data.apellido_mat
            fotografia_usuario=user_data.fotografia_usuario
            promedio_evaluaciones=user_data.promedio_evaluaciones
            nivel_usuario=user_data.nivel_usuario
            codigo_recuperacion=user_data.codigo_recuperacion
            # user_hash
            if user_hash == 'dc243fdf1a24cbced74db81708b30788':
                if action is None:
                    raise web.seeother('/404')
                elif action == 'get':
                    return self.get(nombre_usuario)
                elif action == 'put':
                    return self.put(email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario,codigo_recuperacion)
                elif action == 'delete':
                    return self.delete(nombre_usuario)
                elif action == 'update':
                    return self.update(nombre_usuario, email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario,codigo_recuperacion)
            else:
                raise web.seeother('/404')
        except Exception as e:
            print "WEBSERVICE Error {}".format(e.args)
            raise web.seeother('/404')
