import config
import hashlib
import app

class Insert:

    def __init__(self):
        pass

    '''
    def GET(self):
        if app.session.loggedin is True:
            # session_username = app.session.username
            session_username = app.session.privilege  # get the session_privilege
            if session_username == 0: # admin user
                return self.GET_INSERT() # call GET_INSERT() function
            elif session_username == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    def POST(self):
        if app.session.loggedin is True: # validate if the user is logged
            # session_username = app.session.username
            session_username = app.session.privilege # get the session_privilege
            if session_username == 0: # admin user
                return self.POST_INSERT() # call POST_EDIT function
            elif privilege == 1: # guess user
                raise config.web.seeother('/guess') # render guess.html
        else: # the user dont have logged
            raise config.web.seeother('/login') # render login.html

    @staticmethod
    def GET_INSERT():

    @staticmethod
    def POST_INSERT():
    '''

    def GET(self):
        return config.render.insert() # render insert.html

    def POST(self):
        form = config.web.input() # get form data

        # call model insert_recomendaciones and try to insert new data
        config.model.insert_recomendaciones(
            form['fecha'],form['descripcion'],form['precio'],form['latitud_ubi'],form['longitud_ubi'],form['duracion'],form['id_categoria'],form['id_producto'],form['nombre_usuario'],form['num_megusta'],form['num_comentarios'],form['promedio_evaluaciones'],form['recomendacion_activa'],
        )
        raise config.web.seeother('/recomendaciones') # render recomendaciones index.html
