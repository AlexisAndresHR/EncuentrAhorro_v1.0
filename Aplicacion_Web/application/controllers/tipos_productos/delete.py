import config
import hashlib
import app

class Delete:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_producto, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_DELETE(id_producto) # call GET_DELETE function
            elif privsession_privilegeilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_producto, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege
            if session_privilege == 0: # admin user
                return self.POST_DELETE(id_producto) # call POST_DELETE function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_DELETE(id_producto, **k):

    @staticmethod
    def POST_DELETE(id_producto, **k):
    '''

    def GET(self, id_producto, **k):
        message = None # Error message
        id_producto = config.check_secure_val(str(id_producto)) # HMAC id_producto validate
        result = config.model.get_tipos_productos(int(id_producto)) # search  id_producto
        result.id_producto = config.make_secure_val(str(result.id_producto)) # apply HMAC for id_producto
        return config.render.delete(result, message) # render delete.html with user data

    def POST(self, id_producto, **k):
        form = config.web.input() # get form data
        form['id_producto'] = config.check_secure_val(str(form['id_producto'])) # HMAC id_producto validate
        result = config.model.delete_tipos_productos(form['id_producto']) # get tipos_productos data
        if result is None: # delete error
            message = "El registro no se puede borrar" # Error messate
            id_producto = config.check_secure_val(str(id_producto))  # HMAC user validate
            id_producto = config.check_secure_val(str(id_producto))  # HMAC user validate
            result = config.model.get_tipos_productos(int(id_producto)) # get id_producto data
            result.id_producto = config.make_secure_val(str(result.id_producto)) # apply HMAC to id_producto
            return config.render.delete(result, message) # render delete.html again
        else:
            raise config.web.seeother('/tipos_productos') # render tipos_productos delete.html 
