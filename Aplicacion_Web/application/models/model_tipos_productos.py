import web
import config

db = config.db


def get_all_tipos_productos():
    try:
        return db.select('tipos_productos')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_tipos_productos(id_producto):
    try:
        return db.select('tipos_productos', where='id_producto=$id_producto', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_tipos_productos(id_producto):
    try:
        return db.delete('tipos_productos', where='id_producto=$id_producto', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_tipos_productos(nombre_producto,imagen_producto,id_categoria):
    try:
        return db.insert('tipos_productos',nombre_producto=nombre_producto,
imagen_producto=imagen_producto,
id_categoria=id_categoria)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_tipos_productos(id_producto,nombre_producto,imagen_producto,id_categoria):
    try:
        return db.update('tipos_productos',id_producto=id_producto,
nombre_producto=nombre_producto,
imagen_producto=imagen_producto,
id_categoria=id_categoria,
                  where='id_producto=$id_producto',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
