import web

db_host = 'jlg7sfncbhyvga14.cbetxkdyhwsb.us-east-1.rds.amazonaws.com'
db_name = 'ytv7y945q0jcbkdd'
db_user = 'ud4ovu146rufgin9'
db_pw = 'gtbly06qsbj1gfvz'

db = web.database(
    dbn='mysql',
    host=db_host,
    db=db_name,
    user=db_user,
    pw=db_pw
    )