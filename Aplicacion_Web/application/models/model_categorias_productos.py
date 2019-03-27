import web
import config

db = config.db


def get_all_categorias_productos():
    try:
        return db.select('categorias_productos')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_categorias_productos(id_categoria):
    try:
        return db.select('categorias_productos', where='id_categoria=$id_categoria', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_categorias_productos(id_categoria):
    try:
        return db.delete('categorias_productos', where='id_categoria=$id_categoria', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_categorias_productos(nombre_categoria):
    try:
        return db.insert('categorias_productos',nombre_categoria=nombre_categoria)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_categorias_productos(id_categoria,nombre_categoria):
    try:
        return db.update('categorias_productos',id_categoria=id_categoria,
nombre_categoria=nombre_categoria,
                  where='id_categoria=$id_categoria',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
