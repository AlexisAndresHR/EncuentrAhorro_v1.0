import web
import config

db = config.db


def get_all_recomendaciones():
    try:
        return db.select('recomendaciones')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_recomendaciones(id_recomendacion):
    try:
        return db.select('recomendaciones', where='id_recomendacion=$id_recomendacion', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_recomendaciones(id_recomendacion):
    try:
        return db.delete('recomendaciones', where='id_recomendacion=$id_recomendacion', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_recomendaciones(fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa):
    try:
        return db.insert('recomendaciones',fecha=fecha,
descripcion=descripcion,
precio=precio,
latitud_ubi=latitud_ubi,
longitud_ubi=longitud_ubi,
duracion=duracion,
id_categoria=id_categoria,
id_producto=id_producto,
nombre_usuario=nombre_usuario,
num_megusta=num_megusta,
num_comentarios=num_comentarios,
promedio_evaluaciones=promedio_evaluaciones,
recomendacion_activa=recomendacion_activa)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_recomendaciones(id_recomendacion,fecha,descripcion,precio,latitud_ubi,longitud_ubi,duracion,id_categoria,id_producto,nombre_usuario,num_megusta,num_comentarios,promedio_evaluaciones,recomendacion_activa):
    try:
        return db.update('recomendaciones',id_recomendacion=id_recomendacion,
fecha=fecha,
descripcion=descripcion,
precio=precio,
latitud_ubi=latitud_ubi,
longitud_ubi=longitud_ubi,
duracion=duracion,
id_categoria=id_categoria,
id_producto=id_producto,
nombre_usuario=nombre_usuario,
num_megusta=num_megusta,
num_comentarios=num_comentarios,
promedio_evaluaciones=promedio_evaluaciones,
recomendacion_activa=recomendacion_activa,
                  where='id_recomendacion=$id_recomendacion',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
