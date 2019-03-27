import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, nombre_usuario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(nombre_usuario) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, nombre_usuario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(nombre_usuario) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(nombre_usuario, **k):

    @staticmethod
    def POST_DELETE(nombre_usuario, **k):
    '''

    def GET(self, nombre_usuario, **k):
        message = None # Error message
        nombre_usuario = config.check_secure_val(str(nombre_usuario)) # HMAC nombre_usuario validate
        result = config.model.get_usuarios(int(nombre_usuario)) # search  nombre_usuario
        result.nombre_usuario = config.make_secure_val(str(result.nombre_usuario)) # apply HMAC for nombre_usuario
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, nombre_usuario, **k):
        form = config.web.input() # get form data
        form['nombre_usuario'] = config.check_secure_val(str(form['nombre_usuario'])) # HMAC nombre_usuario validate
        result = config.model.delete_usuarios(form['nombre_usuario']) # get usuarios data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            nombre_usuario = config.check_secure_val(str(nombre_usuario))  # HMAC user validate
            nombre_usuario = config.check_secure_val(str(nombre_usuario))  # HMAC user validate
            result = config.model.get_usuarios(int(nombre_usuario)) # get nombre_usuario data
            result.nombre_usuario = config.make_secure_val(str(result.nombre_usuario)) # apply HMAC to nombre_usuario
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/usuarios') # render usuarios delete.html 
