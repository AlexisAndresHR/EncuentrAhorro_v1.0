import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_recomendacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(id_recomendacion) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_recomendacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(id_recomendacion) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(id_recomendacion, **k):

    @staticmethod
    def POST_DELETE(id_recomendacion, **k):
    '''

    def GET(self, id_recomendacion, **k):
        message = None # Error message
        id_recomendacion = config.check_secure_val(str(id_recomendacion)) # HMAC id_recomendacion validate
        result = config.model.get_recomendaciones(int(id_recomendacion)) # search  id_recomendacion
        result.id_recomendacion = config.make_secure_val(str(result.id_recomendacion)) # apply HMAC for id_recomendacion
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, id_recomendacion, **k):
        form = config.web.input() # get form data
        form['id_recomendacion'] = config.check_secure_val(str(form['id_recomendacion'])) # HMAC id_recomendacion validate
        result = config.model.delete_recomendaciones(form['id_recomendacion']) # get recomendaciones data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            id_recomendacion = config.check_secure_val(str(id_recomendacion))  # HMAC user validate
            id_recomendacion = config.check_secure_val(str(id_recomendacion))  # HMAC user validate
            result = config.model.get_recomendaciones(int(id_recomendacion)) # get id_recomendacion data
            result.id_recomendacion = config.make_secure_val(str(result.id_recomendacion)) # apply HMAC to id_recomendacion
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/recomendaciones') # render recomendaciones delete.html 
