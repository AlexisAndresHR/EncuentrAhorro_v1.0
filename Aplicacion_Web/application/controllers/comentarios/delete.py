import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_comentario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(id_comentario) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_comentario, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(id_comentario) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(id_comentario, **k):

    @staticmethod
    def POST_DELETE(id_comentario, **k):
    '''

    def GET(self, id_comentario, **k):
        message = None # Error message
        id_comentario = config.check_secure_val(str(id_comentario)) # HMAC id_comentario validate
        result = config.model.get_comentarios(int(id_comentario)) # search  id_comentario
        result.id_comentario = config.make_secure_val(str(result.id_comentario)) # apply HMAC for id_comentario
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, id_comentario, **k):
        form = config.web.input() # get form data
        form['id_comentario'] = config.check_secure_val(str(form['id_comentario'])) # HMAC id_comentario validate
        result = config.model.delete_comentarios(form['id_comentario']) # get comentarios data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            id_comentario = config.check_secure_val(str(id_comentario))  # HMAC user validate
            id_comentario = config.check_secure_val(str(id_comentario))  # HMAC user validate
            result = config.model.get_comentarios(int(id_comentario)) # get id_comentario data
            result.id_comentario = config.make_secure_val(str(result.id_comentario)) # apply HMAC to id_comentario
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/comentarios') # render comentarios delete.html 
