import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_favorito, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(id_favorito) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_favorito, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(id_favorito) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(id_favorito, **k):

    @staticmethod
    def POST_DELETE(id_favorito, **k):
    '''

    def GET(self, id_favorito, **k):
        message = None # Error message
        id_favorito = config.check_secure_val(str(id_favorito)) # HMAC id_favorito validate
        result = config.model.get_favoritos(int(id_favorito)) # search  id_favorito
        result.id_favorito = config.make_secure_val(str(result.id_favorito)) # apply HMAC for id_favorito
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, id_favorito, **k):
        form = config.web.input() # get form data
        form['id_favorito'] = config.check_secure_val(str(form['id_favorito'])) # HMAC id_favorito validate
        result = config.model.delete_favoritos(form['id_favorito']) # get favoritos data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            id_favorito = config.check_secure_val(str(id_favorito))  # HMAC user validate
            id_favorito = config.check_secure_val(str(id_favorito))  # HMAC user validate
            result = config.model.get_favoritos(int(id_favorito)) # get id_favorito data
            result.id_favorito = config.make_secure_val(str(result.id_favorito)) # apply HMAC to id_favorito
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/favoritos') # render favoritos delete.html 
