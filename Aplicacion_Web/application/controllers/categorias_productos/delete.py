import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_categoria, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(id_categoria) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_categoria, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(id_categoria) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(id_categoria, **k):

    @staticmethod
    def POST_DELETE(id_categoria, **k):
    '''

    def GET(self, id_categoria, **k):
        message = None # Error message
        id_categoria = config.check_secure_val(str(id_categoria)) # HMAC id_categoria validate
        result = config.model.get_categorias_productos(int(id_categoria)) # search  id_categoria
        result.id_categoria = config.make_secure_val(str(result.id_categoria)) # apply HMAC for id_categoria
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, id_categoria, **k):
        form = config.web.input() # get form data
        form['id_categoria'] = config.check_secure_val(str(form['id_categoria'])) # HMAC id_categoria validate
        result = config.model.delete_categorias_productos(form['id_categoria']) # get categorias_productos data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            id_categoria = config.check_secure_val(str(id_categoria))  # HMAC user validate
            id_categoria = config.check_secure_val(str(id_categoria))  # HMAC user validate
            result = config.model.get_categorias_productos(int(id_categoria)) # get id_categoria data
            result.id_categoria = config.make_secure_val(str(result.id_categoria)) # apply HMAC to id_categoria
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/categorias_productos') # render categorias_productos delete.html 
