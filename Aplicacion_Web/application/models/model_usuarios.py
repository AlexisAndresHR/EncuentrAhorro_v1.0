import web
import config

db = config.db


def get_all_usuarios():
    try:
        return db.select('usuarios')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_usuarios(nombre_usuario):
    try:
        return db.select('usuarios', where='nombre_usuario=$nombre_usuario', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_usuarios(nombre_usuario):
    try:
        return db.delete('usuarios', where='nombre_usuario=$nombre_usuario', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_usuarios(email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario):
    try:
        return db.insert('usuarios',email_usuario=email_usuario,
contrasena_usuario=contrasena_usuario,
nombre=nombre,
apellido_pat=apellido_pat,
apellido_mat=apellido_mat,
fotografia_usuario=fotografia_usuario,
promedio_evaluaciones=promedio_evaluaciones,
nivel_usuario=nivel_usuario)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_usuarios(nombre_usuario,email_usuario,contrasena_usuario,nombre,apellido_pat,apellido_mat,fotografia_usuario,promedio_evaluaciones,nivel_usuario):
    try:
        return db.update('usuarios',nombre_usuario=nombre_usuario,
email_usuario=email_usuario,
contrasena_usuario=contrasena_usuario,
nombre=nombre,
apellido_pat=apellido_pat,
apellido_mat=apellido_mat,
fotografia_usuario=fotografia_usuario,
promedio_evaluaciones=promedio_evaluaciones,
nivel_usuario=nivel_usuario,
                  where='nombre_usuario=$nombre_usuario',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
