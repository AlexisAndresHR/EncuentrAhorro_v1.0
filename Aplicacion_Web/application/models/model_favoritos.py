import web
import config

db = config.db


def get_all_favoritos():
    try:
        return db.select('favoritos')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_favoritos(id_favorito):
    try:
        return db.select('favoritos', where='id_favorito=$id_favorito', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_favoritos(id_favorito):
    try:
        return db.delete('favoritos', where='id_favorito=$id_favorito', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_favoritos(nombre_usuario,id_recomendacion):
    try:
        return db.insert('favoritos',nombre_usuario=nombre_usuario,
id_recomendacion=id_recomendacion)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_favoritos(id_favorito,nombre_usuario,id_recomendacion):
    try:
        return db.update('favoritos',id_favorito=id_favorito,
nombre_usuario=nombre_usuario,
id_recomendacion=id_recomendacion,
                  where='id_favorito=$id_favorito',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
