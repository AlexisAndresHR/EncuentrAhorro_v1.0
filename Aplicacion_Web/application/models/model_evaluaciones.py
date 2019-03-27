import web
import config

db = config.db


def get_all_evaluaciones():
    try:
        return db.select('evaluaciones')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_evaluaciones(id_evaluacion):
    try:
        return db.select('evaluaciones', where='id_evaluacion=$id_evaluacion', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_evaluaciones(id_evaluacion):
    try:
        return db.delete('evaluaciones', where='id_evaluacion=$id_evaluacion', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_evaluaciones(calificacion,id_recomendacion,nombre_usuario):
    try:
        return db.insert('evaluaciones',calificacion=calificacion,
id_recomendacion=id_recomendacion,
nombre_usuario=nombre_usuario)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_evaluaciones(id_evaluacion,calificacion,id_recomendacion,nombre_usuario):
    try:
        return db.update('evaluaciones',id_evaluacion=id_evaluacion,
calificacion=calificacion,
id_recomendacion=id_recomendacion,
nombre_usuario=nombre_usuario,
                  where='id_evaluacion=$id_evaluacion',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
