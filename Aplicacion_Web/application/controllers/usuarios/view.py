import config
import hashlib
import app


class View:

    def __init__(self):
        pass

    '''
    def GET(self, nombre_usuario):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_VIEW(nombre_usuario) # call GET_VIEW() function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_VIEW(nombre_usuario):
    '''

    def GET(self, nombre_usuario):
        nombre_usuario = config.check_secure_val(str(nombre_usuario)) # HMAC nombre_usuario validate
        result = config.model.get_usuarios(nombre_usuario) # search for the nombre_usuario data
        return config.render.view(result) # render view.html with nombre_usuario data
