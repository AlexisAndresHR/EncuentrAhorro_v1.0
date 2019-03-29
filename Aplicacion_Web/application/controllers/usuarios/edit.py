import config
import hashlib
import app

class Edit:
    
    def __init__(self):
        pass

    '''
    def GET(self, nombre_usuario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_EDIT(nombre_usuario) # call GET_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, nombre_usuario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.POST_EDIT(nombre_usuario) # call POST_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_EDIT(nombre_usuario, **k):

    @staticmethod
    def POST_EDIT(nombre_usuario, **k):
        
    '''

    def GET(self, nombre_usuario, **k):
        message = None # Error message
        nombre_usuario = config.check_secure_val(str(nombre_usuario)) # HMAC nombre_usuario validate
        result = config.model.get_usuarios(str(nombre_usuario)) # search for the nombre_usuario
        result.nombre_usuario = config.make_secure_val(str(result.nombre_usuario)) # apply HMAC for nombre_usuario
        return config.render.edit(result, message) # render usuarios edit.html

    def POST(self, nombre_usuario, **k):
        form = config.web.input()  # get form data
        form['nombre_usuario'] = config.check_secure_val(str(form['nombre_usuario'])) # HMAC nombre_usuario validate
        # edit user with new data
        result = config.model.edit_usuarios(
            form['nombre_usuario'],form['email_usuario'],form['contrasena_usuario'],form['nombre'],form['apellido_pat'],form['apellido_mat'],form['fotografia_usuario'],form['promedio_evaluaciones'],form['nivel_usuario'],form['codigo_recuperacion'],
        )
        if result == None: # Error on udpate data
            nombre_usuario = config.check_secure_val(str(nombre_usuario)) # validate HMAC nombre_usuario
            result = config.model.get_usuarios(int(nombre_usuario)) # search for nombre_usuario data
            result.nombre_usuario = config.make_secure_val(str(result.nombre_usuario)) # apply HMAC to nombre_usuario
            message = "Error al editar el registro" # Error message
            return config.render.edit(result, message) # render edit.html again
        else: # update user data succefully
            raise config.web.seeother('/usuarios') # render usuarios index.html
