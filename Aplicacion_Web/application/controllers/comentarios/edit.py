import config
import hashlib
import app

class Edit:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_comentario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_EDIT(id_comentario) # call GET_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_comentario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.POST_EDIT(id_comentario) # call POST_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_EDIT(id_comentario, **k):

    @staticmethod
    def POST_EDIT(id_comentario, **k):
        
    '''

    def GET(self, id_comentario, **k):
        message = None # Error message
        id_comentario = config.check_secure_val(str(id_comentario)) # HMAC id_comentario validate
        result = config.model.get_comentarios(int(id_comentario)) # search for the id_comentario
        result.id_comentario = config.make_secure_val(str(result.id_comentario)) # apply HMAC for id_comentario
        return config.render.edit(result, message) # render comentarios edit.html

    def POST(self, id_comentario, **k):
        form = config.web.input()  # get form data
        form['id_comentario'] = config.check_secure_val(str(form['id_comentario'])) # HMAC id_comentario validate
        # edit user with new data
        result = config.model.edit_comentarios(
            form['id_comentario'],form['id_recomendacion'],form['nombre_usuario'],form['contenido'],form['fecha_comentario'],
        )
        if result == None: # Error on udpate data
            id_comentario = config.check_secure_val(str(id_comentario)) # validate HMAC id_comentario
            result = config.model.get_comentarios(int(id_comentario)) # search for id_comentario data
            result.id_comentario = config.make_secure_val(str(result.id_comentario)) # apply HMAC to id_comentario
            message = "Error al editar el registro" # Error message
            return config.render.edit(result, message) # render edit.html again
        else: # update user data succefully
            raise config.web.seeother('/comentarios') # render comentarios index.html
