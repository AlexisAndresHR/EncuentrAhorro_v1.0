import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_evaluacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(id_evaluacion) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_evaluacion, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(id_evaluacion) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(id_evaluacion, **k):

    @staticmethod
    def POST_DELETE(id_evaluacion, **k):
    '''

    def GET(self, id_evaluacion, **k):
        message = None # Error message
        id_evaluacion = config.check_secure_val(str(id_evaluacion)) # HMAC id_evaluacion validate
        result = config.model.get_evaluaciones(int(id_evaluacion)) # search  id_evaluacion
        result.id_evaluacion = config.make_secure_val(str(result.id_evaluacion)) # apply HMAC for id_evaluacion
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, id_evaluacion, **k):
        form = config.web.input() # get form data
        form['id_evaluacion'] = config.check_secure_val(str(form['id_evaluacion'])) # HMAC id_evaluacion validate
        result = config.model.delete_evaluaciones(form['id_evaluacion']) # get evaluaciones data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            id_evaluacion = config.check_secure_val(str(id_evaluacion))  # HMAC user validate
            id_evaluacion = config.check_secure_val(str(id_evaluacion))  # HMAC user validate
            result = config.model.get_evaluaciones(int(id_evaluacion)) # get id_evaluacion data
            result.id_evaluacion = config.make_secure_val(str(result.id_evaluacion)) # apply HMAC to id_evaluacion
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/evaluaciones') # render evaluaciones delete.html 
