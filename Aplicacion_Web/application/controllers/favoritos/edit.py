import config
import hashlib
import app

class Edit:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_favorito, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_EDIT(id_favorito) # call GET_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_favorito, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.POST_EDIT(id_favorito) # call POST_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_EDIT(id_favorito, **k):

    @staticmethod
    def POST_EDIT(id_favorito, **k):
        
    '''

    def GET(self, id_favorito, **k):
        message = None # Error message
        id_favorito = config.check_secure_val(str(id_favorito)) # HMAC id_favorito validate
        result = config.model.get_favoritos(int(id_favorito)) # search for the id_favorito
        result.id_favorito = config.make_secure_val(str(result.id_favorito)) # apply HMAC for id_favorito
        return config.render.edit(result, message) # render favoritos edit.html

    def POST(self, id_favorito, **k):
        form = config.web.input()  # get form data
        form['id_favorito'] = config.check_secure_val(str(form['id_favorito'])) # HMAC id_favorito validate
        # edit user with new data
        result = config.model.edit_favoritos(
            form['id_favorito'],form['nombre_usuario'],form['id_recomendacion'],
        )
        if result == None: # Error on udpate data
            id_favorito = config.check_secure_val(str(id_favorito)) # validate HMAC id_favorito
            result = config.model.get_favoritos(int(id_favorito)) # search for id_favorito data
            result.id_favorito = config.make_secure_val(str(result.id_favorito)) # apply HMAC to id_favorito
            message = "Error al editar el registro" # Error message
            return config.render.edit(result, message) # render edit.html again
        else: # update user data succefully
            raise config.web.seeother('/favoritos') # render favoritos index.html
