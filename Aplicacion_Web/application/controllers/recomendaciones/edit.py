import config
import hashlib
import app

class Edit:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_recomendacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_EDIT(id_recomendacion) # call GET_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_recomendacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.POST_EDIT(id_recomendacion) # call POST_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_EDIT(id_recomendacion, **k):

    @staticmethod
    def POST_EDIT(id_recomendacion, **k):
        
    '''

    def GET(self, id_recomendacion, **k):
        message = None # Error message
        id_recomendacion = config.check_secure_val(str(id_recomendacion)) # HMAC id_recomendacion validate
        result = config.model.get_recomendaciones(int(id_recomendacion)) # search for the id_recomendacion
        result.id_recomendacion = config.make_secure_val(str(result.id_recomendacion)) # apply HMAC for id_recomendacion
        return config.render.edit(result, message) # render recomendaciones edit.html

    def POST(self, id_recomendacion, **k):
        form = config.web.input()  # get form data
        form['id_recomendacion'] = config.check_secure_val(str(form['id_recomendacion'])) # HMAC id_recomendacion validate
        # edit user with new data
        result = config.model.edit_recomendaciones(
            form['id_recomendacion'],form['fecha'],form['descripcion'],form['precio'],form['latitud_ubi'],form['longitud_ubi'],form['duracion'],form['id_categoria'],form['id_producto'],form['nombre_usuario'],form['num_megusta'],form['num_comentarios'],form['promedio_evaluaciones'],form['recomendacion_activa'],
        )
        if result == None: # Error on udpate data
            id_recomendacion = config.check_secure_val(str(id_recomendacion)) # validate HMAC id_recomendacion
            result = config.model.get_recomendaciones(int(id_recomendacion)) # search for id_recomendacion data
            result.id_recomendacion = config.make_secure_val(str(result.id_recomendacion)) # apply HMAC to id_recomendacion
            message = "Error al editar el registro" # Error message
            return config.render.edit(result, message) # render edit.html again
        else: # update user data succefully
            raise config.web.seeother('/recomendaciones') # render recomendaciones index.html
