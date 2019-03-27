import config
import hashlib
import app

class Edit:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_evaluacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_EDIT(id_evaluacion) # call GET_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_evaluacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.POST_EDIT(id_evaluacion) # call POST_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_EDIT(id_evaluacion, **k):

    @staticmethod
    def POST_EDIT(id_evaluacion, **k):
        
    '''

    def GET(self, id_evaluacion, **k):
        message = None # Error message
        id_evaluacion = config.check_secure_val(str(id_evaluacion)) # HMAC id_evaluacion validate
        result = config.model.get_evaluaciones(int(id_evaluacion)) # search for the id_evaluacion
        result.id_evaluacion = config.make_secure_val(str(result.id_evaluacion)) # apply HMAC for id_evaluacion
        return config.render.edit(result, message) # render evaluaciones edit.html

    def POST(self, id_evaluacion, **k):
        form = config.web.input()  # get form data
        form['id_evaluacion'] = config.check_secure_val(str(form['id_evaluacion'])) # HMAC id_evaluacion validate
        # edit user with new data
        result = config.model.edit_evaluaciones(
            form['id_evaluacion'],form['calificacion'],form['id_recomendacion'],form['nombre_usuario'],
        )
        if result == None: # Error on udpate data
            id_evaluacion = config.check_secure_val(str(id_evaluacion)) # validate HMAC id_evaluacion
            result = config.model.get_evaluaciones(int(id_evaluacion)) # search for id_evaluacion data
            result.id_evaluacion = config.make_secure_val(str(result.id_evaluacion)) # apply HMAC to id_evaluacion
            message = "Error al editar el registro" # Error message
            return config.render.edit(result, message) # render edit.html again
        else: # update user data succefully
            raise config.web.seeother('/evaluaciones') # render evaluaciones index.html
