# Author : Salvador Hernandez Mendoza
# Email  : salvadorhm@gmail.com
# Twitter: @salvadorhm
import web
import config


#activate ssl certificate
ssl = False

urls = (
    '/', 'application.controllers.main.index.Index',
    '/login', 'application.controllers.main.login.Login',
    '/logout', 'application.controllers.main.logout.Logout',
    '/users', 'application.controllers.users.index.Index',
    '/users/printer', 'application.controllers.users.printer.Printer',
    '/users/view/(.+)', 'application.controllers.users.view.View',
    '/users/edit/(.+)', 'application.controllers.users.edit.Edit',
    '/users/delete/(.+)', 'application.controllers.users.delete.Delete',
    '/users/insert', 'application.controllers.users.insert.Insert',
    '/users/change_pwd', 'application.controllers.users.change_pwd.Change_pwd',
    '/logs', 'application.controllers.logs.index.Index',
    '/logs/printer', 'application.controllers.logs.printer.Printer',
    '/logs/view/(.+)', 'application.controllers.logs.view.View',
    '/categorias_productos', 'application.controllers.categorias_productos.index.Index',
    '/categorias_productos/view/(.+)', 'application.controllers.categorias_productos.view.View',
    '/categorias_productos/edit/(.+)', 'application.controllers.categorias_productos.edit.Edit',
    '/categorias_productos/delete/(.+)', 'application.controllers.categorias_productos.delete.Delete',
    '/categorias_productos/insert', 'application.controllers.categorias_productos.insert.Insert',
    '/comentarios', 'application.controllers.comentarios.index.Index',
    '/comentarios/view/(.+)', 'application.controllers.comentarios.view.View',
    '/comentarios/edit/(.+)', 'application.controllers.comentarios.edit.Edit',
    '/comentarios/delete/(.+)', 'application.controllers.comentarios.delete.Delete',
    '/comentarios/insert', 'application.controllers.comentarios.insert.Insert',
    '/evaluaciones', 'application.controllers.evaluaciones.index.Index',
    '/evaluaciones/view/(.+)', 'application.controllers.evaluaciones.view.View',
    '/evaluaciones/edit/(.+)', 'application.controllers.evaluaciones.edit.Edit',
    '/evaluaciones/delete/(.+)', 'application.controllers.evaluaciones.delete.Delete',
    '/evaluaciones/insert', 'application.controllers.evaluaciones.insert.Insert',
    '/favoritos', 'application.controllers.favoritos.index.Index',
    '/favoritos/view/(.+)', 'application.controllers.favoritos.view.View',
    '/favoritos/edit/(.+)', 'application.controllers.favoritos.edit.Edit',
    '/favoritos/delete/(.+)', 'application.controllers.favoritos.delete.Delete',
    '/favoritos/insert', 'application.controllers.favoritos.insert.Insert',
    '/recomendaciones', 'application.controllers.recomendaciones.index.Index',
    '/recomendaciones/view/(.+)', 'application.controllers.recomendaciones.view.View',
    '/recomendaciones/edit/(.+)', 'application.controllers.recomendaciones.edit.Edit',
    '/recomendaciones/delete/(.+)', 'application.controllers.recomendaciones.delete.Delete',
    '/recomendaciones/insert', 'application.controllers.recomendaciones.insert.Insert',
    '/tipos_productos', 'application.controllers.tipos_productos.index.Index',
    '/tipos_productos/view/(.+)', 'application.controllers.tipos_productos.view.View',
    '/tipos_productos/edit/(.+)', 'application.controllers.tipos_productos.edit.Edit',
    '/tipos_productos/delete/(.+)', 'application.controllers.tipos_productos.delete.Delete',
    '/tipos_productos/insert', 'application.controllers.tipos_productos.insert.Insert',
    '/usuarios', 'application.controllers.usuarios.index.Index',
    '/usuarios/view/(.+)', 'application.controllers.usuarios.view.View',
    '/usuarios/edit/(.+)', 'application.controllers.usuarios.edit.Edit',
    '/usuarios/delete/(.+)', 'application.controllers.usuarios.delete.Delete',
    '/usuarios/insert', 'application.controllers.usuarios.insert.Insert',
)

app = web.application(urls, globals())

if ssl is True:
    from web.wsgiserver import CherryPyWSGIServer
    CherryPyWSGIServer.ssl_certificate = "ssl/server.crt"
    CherryPyWSGIServer.ssl_private_key = "ssl/server.key"

if web.config.get('_session') is None:
    db = config.db
    store = web.session.DBStore(db, 'sessions')
    session = web.session.Session(
        app,
        store,
        initializer={
        'login': 0,
        'privilege': -1,
        'user': 'anonymous',
        'loggedin': False,
        'count': 0
        }
        )
    web.config._session = session
    web.config.session_parameters['cookie_name'] = 'kuorra'
    web.config.session_parameters['timeout'] = 10
    web.config.session_parameters['expired_message'] = 'Session expired'
    web.config.session_parameters['ignore_expiry'] = False
    web.config.session_parameters['ignore_change_ip'] = False
    web.config.session_parameters['secret_key'] = 'fLjUfxqXtfNoIldA0A0J'
else:
    session = web.config._session


class Count:
    def GET(self):
        session.count += 1
        return str(session.count)


def InternalError(): 
    raise config.web.seeother('/')


def NotFound():
    raise config.web.seeother('/')

if __name__ == "__main__":
    db.printing = False # hide db transactions
    web.config.debug = False # hide debug print
    web.config.db_printing = False # hide db transactions
    app.internalerror = InternalError
    app.notfound = NotFound
    app.run()
