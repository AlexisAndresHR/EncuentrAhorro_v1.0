import config
import hashlib
import app

class Edit:
    
    def __init__(self):
        pass

    '''
    def GET(self, id_categoria, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.GET_EDIT(id_categoria) # call GET_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self, id_categoria, **k):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_privilege = app.session.privilege # get the session_privilege
            if session_privilege == 0: # admin user
                return self.POST_EDIT(id_categoria) # call POST_EDIT function
            elif session_privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_EDIT(id_categoria, **k):

    @staticmethod
    def POST_EDIT(id_categoria, **k):
        
    '''

    def GET(self, id_categoria, **k):
        message = None # Error message
        id_categoria = config.check_secure_val(str(id_categoria)) # HMAC id_categoria validate
        result = config.model.get_categorias_productos(int(id_categoria)) # search for the id_categoria
        result.id_categoria = config.make_secure_val(str(result.id_categoria)) # apply HMAC for id_categoria
        return config.render.edit(result, message) # render categorias_productos edit.html

    def POST(self, id_categoria, **k):
        form = config.web.input()  # get form data
        form['id_categoria'] = config.check_secure_val(str(form['id_categoria'])) # HMAC id_categoria validate
        # edit user with new data
        result = config.model.edit_categorias_productos(
            form['id_categoria'],form['nombre_categoria'],
        )
        if result == None: # Error on udpate data
            id_categoria = config.check_secure_val(str(id_categoria)) # validate HMAC id_categoria
            result = config.model.get_categorias_productos(int(id_categoria)) # search for id_categoria data
            result.id_categoria = config.make_secure_val(str(result.id_categoria)) # apply HMAC to id_categoria
            message = "Error al editar el registro" # Error message
            return config.render.edit(result, message) # render edit.html again
        else: # update user data succefully
            raise config.web.seeother('/categorias_productos') # render categorias_productos index.html
