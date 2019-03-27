import web
import config

db = config.db


def get_all_comentarios():
    try:
        return db.select('comentarios')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_comentarios(id_comentario):
    try:
        return db.select('comentarios', where='id_comentario=$id_comentario', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_comentarios(id_comentario):
    try:
        return db.delete('comentarios', where='id_comentario=$id_comentario', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_comentarios(id_recomendacion,nombre_usuario,contenido):
    try:
        return db.insert('comentarios',id_recomendacion=id_recomendacion,
nombre_usuario=nombre_usuario,
contenido=contenido)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_comentarios(id_comentario,id_recomendacion,nombre_usuario,contenido):
    try:
        return db.update('comentarios',id_comentario=id_comentario,
id_recomendacion=id_recomendacion,
nombre_usuario=nombre_usuario,
contenido=contenido,
                  where='id_comentario=$id_comentario',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
