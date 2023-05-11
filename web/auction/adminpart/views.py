import datetime
from django.http import HttpResponse
from django.shortcuts import render
from adminpart.models import *
from django.core.files.storage import FileSystemStorage
from django.db import connection
def login1(request):
    return render(request,'login.html')
     
def logincode(request):
    usrnam = request.POST['textfield']
    pas = request.POST['textfield2']

    try:
        ob=login.objects.get(username=usrnam,password=pas)
        print(ob,"========================")
        if ob.usertype == 'admin':
            return HttpResponse('''<script> alert ("welcome");window.location='/hom1'</script>)''')
        else:
            return HttpResponse('''<script> alert ("Error");
            window.location='/'</script>)''')
    except Exception as e:
        print(e)
        print(e)
        print(e)
        return HttpResponse('''<script> alert ("error");
            window.location='/'</script>)''')

def hom(request):
    return render(request,'homepage.html')

def hom1(request):
    return render(request,'homepage1.html')


def cat(request):
    ob=category.objects.all()
    return render(request,"category.html",{'val':ob})
    
def adcat(request):
    return render(request,'addcategory.html')

def  adnewcat(request):
    nam = request.POST['name'] 
    image = request.FILES['file']
    try:
        fp=FileSystemStorage()
        fs=fp.save(image.name,image)
        ob=category()
        ob.category_nam=nam
        ob.image=image
        ob.save()
        return HttpResponse('''<script> alert ("Added");
                window.location='/cat'</script>)''')
    except:
         return HttpResponse('''<script> alert ("category already added"); window.location='/cat'</script>)''')
         

# def viewcat(request):
#     ob=category.objects.all()
#     return render(request,"category.html",{'val':ob})

def upcat(request, id):
    ob = category.objects.get(id=id)
    request.session['cid'] = id
    return render(request,'updatecat.html', {'val': ob})

def updatecatg(request):
     try:
        nam = request.POST['name']
        image = request.FILES['file']
        fp=FileSystemStorage()
        fs=fp.save(image.name,image)
        iob = category.objects.get(id=request.session['cid'])
        iob.category_nam = nam
        iob.image = image
        iob.save()
        return HttpResponse('''<script> alert ("Updated"); window.location='/cat'</script>)''')
     except:
         nam = request.POST['name']
         iob = category.objects.get(id=request.session['cid'])
         iob.category_nam = nam
         iob.save()
         return HttpResponse('''<script> alert ("Updated"); window.location='/cat'</script>)''')
         

def viewuser(request):
     ob=registration.objects.all()
     return render(request,"viewusers.html",{'val':ob})


def viewpro(request):
    b=product.objects.all()
    return render(request,"products.html",{'val':b})

def removepro(request,id):
    ob = product.objects.get(id=id)
    
    # ob.delete()
    request.session['pid'] = id
    return render(request,"removepro1.html")


def removeproduct(request):
   ob1=product.objects.get(id=request.session['pid'])
   ob1.delete()
   message = f'''Your product has been removed'''
   send_mail('Bid 365',"your product has been removed",'alenchristy0201@gmail.com',['alenchristy0201@gmail.com'], fail_silently=False)
   return HttpResponse('''<script> alert ("removed"); window.location='/viewpro'</script>)''')


from django.shortcuts import render
from .models import registration, feedback
def feed(request):
    with connection.cursor() as cursor:
        cursor.execute('SELECT `adminpart_registration`.`firstname`,`adminpart_registration`.`lastname`,`adminpart_feedback`.`feedb`,`adminpart_feedback`.`date` FROM `adminpart_feedback` JOIN `adminpart_registration` ON `adminpart_feedback`.`user_id`=`adminpart_registration`.`id`')
        b = cursor.fetchall()
        print(b,"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")

    # b=feedback.objects.all()
    return render(request,"feedback.html",{'val':b})

def auction2(request):
    # b=product.objects.all()
    return render(request,"auction.html")

def auction1(request):
    with connection.cursor() as cursor:
        cursor.execute('SELECT `adminpart_product`.`pro_name`,`adminpart_product`.`price`,`adminpart_product`.`rprice`,`adminpart_bid`.`amt`,`adminpart_registration`.`firstname`,`adminpart_bid`.`status`,`adminpart_bid`.`adate`,`adminpart_bid`.`amt`-`adminpart_product`.`price` AS profit FROM `adminpart_product` JOIN `adminpart_bid` ON `adminpart_product`.`id`=`adminpart_bid`.`pid_id` JOIN `adminpart_registration` ON `adminpart_bid`.`user_id`=`adminpart_registration`.id')
        b = cursor.fetchall()
    return render(request,"auction1.html",{'val':b})


def earnings(request):
    with connection.cursor() as cursor:
        cursor.execute('SELECT `adminpart_registration`.`firstname`,`adminpart_pay`.`pname`,`adminpart_pay`.`date`,`adminpart_pay`.`amt` FROM `adminpart_pay` JOIN `adminpart_registration` ON `adminpart_pay`.`user_id`=`adminpart_registration`.`id`')
        b = cursor.fetchall()
        total=0
        for i in b:
            total=total+i[3]
    
    return render(request,"earnings.html",{'val':b,"total":total}) 
    







    

# def contract(request):
#     lid=request.POST['lid']
#     rr=registration.objects.get(login__id=lid)
#     uid=rr.id
#     data=[]
#     ob=bid.objects.get(status='paid',user__id='4')
#     by1=ob.user.firstname
#     amt=ob.amt
#     sell=ob.pid.user.firstname
#     pn=ob.pid.pro_name
#     print(by1,"##################################################################")
#     return render(request,'contract.html', {'sell':sell,'pn':pn,'amt':amt,'buyr':by1})







###########################webservice
from django.core import serializers
import json
from django.http import JsonResponse
from django.db.models.fields.files import ImageFieldFile

def android_login(request):
    try:
        print(request.POST)
        un=request.POST['username']
        pwd=request.POST['password']
        print(un,pwd)
        user = login.objects.get( username=un, password=pwd,usertype='user')
    
        if user is None:
                data = {"result": "invalid"}
        else:
                print("in user function")
                data = {"result":"valid","id":str(user.id)}
        r = json.dumps(data)
        print (r)
        return HttpResponse(r)
    except:
        data = {"result": "invalid"}
        r = json.dumps(data)
        print (r)
        return HttpResponse(r)


def reg(request):

    print (request.POST)
    username=request.POST['username']
    password=request.POST['password']
    first_name=request.POST['firstname']
    last_name=request.POST['lastname']
    gen=request.POST['gender']
    addr=request.POST['addr'] 
    phn=request.POST['phone']
    email=request.POST['email']
    lob = login()
    lob.username = username
    lob.password = password
    lob.usertype = 'user'
    lob.save()

    userob=registration()
    userob.firstname=first_name
    userob.lastname=last_name
    userob.gender=gen
    userob.address=addr
    userob.phone=phn
    userob.email=email
    userob.login=lob
    userob.save()
    data = {"task":"success"}
    r = json.dumps(data)
    return HttpResponse(r)


def view_profile(request):
    id=request.POST['lid']
    i=registration.objects.get(login__id=id)
    data=[]
    row = {"fname": i.firstname, "lname":i.lastname, "adrs":i.address, "phon":i.phone, "gender":i.gender, "email":i.email, "id" : i.login.id}
    data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)

def updateprofile(request):
    id=request.POST['lid']
    fname = request.POST['fname']
    lname = request.POST['lname']
    adres = request.POST['adres']
    gend = request.POST['gender']
    ph = request.POST['phone']
    eml = request.POST['email']

    fob = registration.objects.get(login__id=id)
    fob.lastname = fname
    fob.firstname = lname
    fob.address = adres
    fob.gender = gend
    fob.phone = ph
    fob.email = eml
    fob.save()
    data = {"task" : "success"}
    
    r = json.dumps(data)  
    return HttpResponse(r)

def viewcatg(request):
    ob=category.objects.all()
    data=[]
    for i in ob:
        row = {"id": i.id, "c_name": i.category_nam}
        data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)


def adproduct(request):
    id=request.POST['lid']
    cid=request.POST['ccid']
    pname = request.POST['name']
    detail = request.POST['detail']
    price = request.POST['price']
    rp = request.POST['rp']
    date = request.POST['date']
    time= request.POST['time']
    image = request.FILES['file']
    place = request.POST['place']
    fp=FileSystemStorage()
    fs=fp.save(image.name,image)
    ob=product()
    ob.pro_name=pname
    ob.details=detail
    ob.price=price
    ob.rprice=rp
    ob.image=fs
    ob.status='pending'
    ob.place=place
    ob.catg=category.objects.get(id=cid)
    ob.user=registration.objects.get(login__id=id)
    ob.date=date
    ob.time=time
    ob.save()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r)  



def viewuserprodct(request):
    id=request.POST['lid']
    ob=product.objects.filter(user__login__id=id)
    
    data=[]
    for i in ob:
        row = {"pname": i.pro_name, "price":i.price, "image":str(i.image), "id" : i.id}
        data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)

def deleteproduct(request):
    id=request.POST['pid']
    ob=product.objects.get(id=id)
    ob.delete()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r)

def updateview(request):
    id=request.POST['pid']
    i=product.objects.get(id=id)
    data=[]
    row = {"pname":i.pro_name, "details":i.details, "image":str(i.image), "date":i.date, "price":i.price,"time":i.time, "place":i.place, "cat":i.catg.category_nam, "id" : i.id}
    data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)

def updateproduct(request):
    id=request.POST['pid']
    id1=request.POST['lid']
    cid=request.POST['ccid']
    name = request.POST['name']
    details = request.POST['detail']
    price = request.POST['price']
    date = request.POST['date']
    time= request.POST['time']
    image = request.FILES['file']
    place = request.POST['place']
    fp=FileSystemStorage()
    fs=fp.save(image.name,image)


    ob = product.objects.get(id=id)
    ob.pro_name=name
    ob.details=details
    ob.price=price
    ob.image=fs
    ob.status='pending'
    ob.place=place
    ob.catg=category.objects.get(id=cid)
    ob.user=registration.objects.get(login__id=id1)
    ob.date=date
    ob.time=time
    ob.save()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r) 

    


def updateproduct2(request):
    id=request.POST['pid']
    id1=request.POST['lid']
    cid=request.POST['ccid']
    name = request.POST['name']
    details = request.POST['detail']
    price = request.POST['price']
    date = request.POST['date']
    time= request.POST['time']
   
    place = request.POST['place']
    

    ob = product.objects.get(id=id)
    ob.pro_name=name
    ob.details=details
    ob.price=price
   
    ob.status='pending'
    ob.place=place
    ob.catg=category.objects.get(id=cid)
    ob.user=registration.objects.get(login__id=id1)
    ob.date=date
    ob.time=time
    ob.save()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r) 

    

def select_home(request):
    lid=request.POST['lid']

    ob=category.objects.all()
    data=[]
    for i in ob:
       
            row = {"id": i.id, "c_name": i.category_nam,"img":i.image.url}
            data.append(row)
    ob1=product.objects.exclude(user__login__id=lid).exclude(status='sold')
    data1=[]
    for i in ob1:
            status="0"
            obb=wishlist.objects.filter(user__login__id=lid,product__id=i.id)
            if len(obb)>0:
                status="1"
            row = {"id": i.id, "pname": i.pro_name,"img":i.image.url,"price":i.price,"loc":i.place,"status":status}
            data1.append(row)
    d={"cat":data,"pro":data1}
    r=json.dumps(d)
    print(r)
    return HttpResponse (r)


def viewcatprodct(request):
    id=request.POST['cid']
    
    ob=product.objects.filter(catg__category__id=id)
    
    data=[]
    for i in ob:
        row = {"pname": i.pro_name, "price":i.price, "image":str(i.image), "id" : i.id}
        data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)


def search_view(request):
    txt=request.POST['txt']
    lid=request.POST['lid']
    from django.db import connection
    cursor=connection.cursor()
    sen_res=[]
    cursor.execute("SELECT `adminpart_product`.`pro_name`,`adminpart_product`.`price`,`adminpart_product`.`image`,`adminpart_product`.`place`,`adminpart_product`.`id`,`adminpart_product`.`status` FROM `adminpart_product` WHERE `pro_name` LIKE '%"+str(txt)+"%' and adminpart_product.status!='sold'")
    res=cursor.fetchall()
    print(res,"==========================")
    data = []
    for a in res:
        status="0"
        obb=wishlist.objects.filter(user__login__id=lid,product__id=a[4])
        if len(obb)>0:
            status="1"
        row = {"pname":a[0], "price": a[1],"loc":a[3],"img":"/media/"+a[2],"id":a[4],"status":status}
        data.append(row)
    r = json.dumps(data)
    return HttpResponse(r)


    
    # ob1=product.objects.exclude(user__login__id=lid).exclude(status='sold')
    # data1=[]
    # for i in ob1:
    #         status="0"
    #         obb=wishlist.objects.filter(user__login__id=lid,product__id=i.id)
    #         if len(obb)>0:
    #             status="1"
    #         row = {"id": i.id, "pname": i.pro_name,"img":i.image.url,"price":i.price,"loc":i.place,"status":status}
    #         data1.append(row)
    # d={"cat":data,"pro":data1}
    # r=json.dumps(d)
    # print(r)
    # return HttpResponse (r)
    


def insert_wish(request):
    id=request.POST['pid']
    lid=request.POST['lid']
    ob=wishlist()
    ob.user=registration.objects.get(login__id=lid)
    ob.product=product.objects.get(id=id)
    ob.save()
    data = {"result" : "success"}
    r = json.dumps(data)
    return HttpResponse(r)



def delete_wish(request):
    id=request.POST['pid']
    lid=request.POST['lid']
    ob=wishlist.objects.get(user__login__id=lid,product__id=id)
    ob.delete()
    data = {"result" : "success"}
    r = json.dumps(data)
    return HttpResponse(r)

from django.db.models import Avg
from django.shortcuts import render
from .models import product, rating


def productview(request):
    id=request.POST['pid']
    with connection.cursor() as cursor:
        cursor.execute("SELECT `adminpart_product`.`pro_name`, `adminpart_product`.`details`, `adminpart_product`.`price`, `adminpart_product`.`image`, `adminpart_product`.`place`, `adminpart_product`.`date`, `adminpart_product`.`time`, AVG(`adminpart_rating`.`rate`) AS ratng FROM `adminpart_product` left JOIN `adminpart_rating` ON `adminpart_product`.`user_id` = `adminpart_rating`.`sl_id` WHERE `adminpart_product`.`id` = %s", [id])
        rows = cursor.fetchall()
        data = []
        for i in rows:
            row = {"pname": i[0], "details": i[1], "image": str(i[3]), "date": i[5], "price": i[2], "time": i[6], "place": i[4], "rating": i[7]}
            data.append(row)
    r = json.dumps(data)
    # send_mail('Bid 365', "hhghjgkjgkjghouyouy", 'alenchristy0201@gmail.com',['aswinak799@gmail.com'], fail_silently=False)
    print(r,"==========================================")
    return HttpResponse (r)

    # data=[]
    # row = {"pname":i.pro_name, "details":i.details, "image":str(i.image), "date":i.date, "price":i.price,"time":i.time, "place":i.place, "id" : i.id}
    
    # data.append(row)
    # r=json.dumps(data)
    


def viewcatprodct1(request):
    id=request.POST['cid']
    lid=request.POST['lid']
    ob=product.objects.filter(catg__id=id)
    data1=[]
    for i in ob:
            status="0"
            obb=wishlist.objects.filter(user__login__id=lid,product__id=i.id)
            if len(obb)>0:
                status="1"
            row = {"id": i.id, "pname": i.pro_name,"loc":i.place,"img":i.image.url,"price":i.price,"status":status}
            data1.append(row)
    
    r=json.dumps(data1)
    return HttpResponse (r)


def viewprodct2(request):
    id=request.POST['lid']
    
    ob=wishlist.objects.filter(user__login__id=id)
    print(ob[0].product.pro_name,"************************************")
    
    data=[]
    for i in ob:
        row = {"pname": i.product.pro_name, "price":i.product.price, "image":str(i.product.image), "id" : i.product.id}
        data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)

def checkservice(request):
    
    lid=request.POST['lid']
    u=registration.objects.get(login__id=lid)
    lid=u.id
    rdate = str(datetime.datetime.now().date())
    qry="SELECT `pro_name`,`date`,`time`,id,HOUR(TIMEDIFF(`time`,CURTIME())),MINUTE(TIMEDIFF(`time`,CURTIME())),CURTIME() FROM `adminpart_product` WHERE `date`=CURDATE() AND `id` IN(SELECT `pid_id` FROM `adminpart_register_auction` WHERE `user_id`="+str(lid)+")  AND HOUR(TIMEDIFF(`time`,CURTIME()))=0 AND MINUTE(TIMEDIFF(`time`,CURTIME()))<35 AND  MINUTE(TIMEDIFF(`time`,CURTIME()))>0 AND `id` NOT IN(SELECT `pid_id` FROM `adminpart_notf` WHERE `user_id`="+str(lid)+" )"

    with connection.cursor() as cursor:
        cursor.execute(qry)
        chatt = cursor.fetchall()
        print(chatt,"================")
        if len(chatt)>0:
            obb=notf()
            obp=product.objects.get(id=chatt[0][3])
            print("======",obp)
            obb.user=u
            obb.pid=obp
            obb.save()

            data = {"task" : "yes","msg":""+chatt[0][0]+"'s auction will start on "+chatt[0][2]}
            r = json.dumps(data)
            return HttpResponse(r) 
    


    data = {"task" : "na"}
    r = json.dumps(data)
    return HttpResponse(r) 

def chats(request):
    print(request.POST)
    print(request.POST)
    print(request.POST)
    print(request.POST)
    print(request.POST)
    print(request.POST)
    print(request.POST)
    fid=request.POST['fid']
    tid=request.POST['toid']
    print(tid,"+++++++++++++++")   

    msg=request.POST['msg']
    pid=request.POST['pid']

    date = str(datetime.datetime.now().date())
    time = str(datetime.datetime.now().time())
    print(date,time)
    onf=registration.objects.get(login__id=fid)
    
    obp=product.objects.get(id=pid)
    # toid=user.login.id

    if tid == "0":
        ont=registration.objects.get(login__id=obp.user.login.id)
    else:
        print("hellooooooooooooooooo")
        ont=registration.objects.get(login__id=tid)
    
    ob=chat()
    ob.fid=registration.objects.get(login__id=fid)
    ob.tid=ont
    ob.doubt=msg
    ob.product=obp
    ob.cdate=date
    ob.ctime=time
    ob.reply='pending'

    ob.save()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r) 


def view_message2(request):
    print(request.POST)
    fid=request.POST['fid']
    pid=request.POST['pid']  
    tid=request.POST['toid']
    print(tid)
    mid=request.POST['lastmsgid']  
    onf=registration.objects.get(login__id=fid)    
    fid=str(onf.id)
    obp=product.objects.get(id=pid)
    if tid != "0":
        tob=registration.objects.get(login__id=tid)
        tid=str(tob.id)
        print(tid,"****************")
    else:    
        ont=obp.user
        tid=str(ont.id)
        print(tid)
    print(fid,tid,pid,mid)
    with connection.cursor() as cursor:
        cursor.execute("SELECT `adminpart_chat`.* FROM `adminpart_chat` WHERE `product_id`="+str(pid)+" AND `id`>"+str(mid)+" AND ((`fid_id`="+str(fid)+" AND `tid_id`="+tid+") OR (`fid_id`="+tid+" AND `tid_id`="+fid+"))")
        chatt = cursor.fetchall()
        print(chatt,"==========================")
        result=[]
        rr=[]
        for i in chatt:
            if str(i[5])==fid:
                print("hiiiiii")
                rr=str(i[4])
                rrr=rr.split(".")
                print(rrr,"================================")
                row={"date":str(i[3]),"message":i[1],"fromid":i[5],"msgid":i[0],"ty":"m","time":str(rrr[0])}
                result.append(row)
            else:
                rr=str(i[4])
                rrr=rr.split(".")
                print(rrr,"***************")
                row={"date":str(i[3]),"message":i[1],"fromid":i[5],"msgid":i[0],"ty":"o","time":str(rrr[0])}
                result.append(row)
        if len(result)>0:
            r = json.dumps({"status":"ok","res1":result})
            print(r)
            return HttpResponse(r) 
        else:
            r = json.dumps({"status":"na"})
            print(r)
            return HttpResponse(r) 


def viewchats(request):
    pid=request.POST['pid']
    lid=request.POST['lid']
    data=[]
    with connection.cursor() as cursor:
        cursor.execute("SELECT DISTINCT * FROM `adminpart_registration` WHERE `login_id`!="+str(lid)+" AND `id` IN(SELECT `fid_id` FROM `adminpart_chat` WHERE `product_id`="+str(pid)+")")
        chatt = cursor.fetchall()
        for i in chatt:
            print(i,"****************************")
            row = {"pname": i[2]+" "+i[1],'uid':i[7]}
            data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)


def regauction(request):
    pid=request.POST['pid']
    lid=request.POST['lid']
    rdate = str(datetime.datetime.now().date())
    ob=register_auction()
    ob.user=registration.objects.get(login__id=lid)
    ob.pid=product.objects.get(id=pid)
    ob.date=rdate
    ob.status='registerd'
    ob.save()
    data = {"result" : "success"}
    r = json.dumps(data)
    return HttpResponse(r)
    
    
def regauction1(request):
    pid=request.POST['pid']
    lid=request.POST['lid']
    obb=register_auction.objects.filter(user__login__id=lid,pid__id=pid)
    if len(obb)==0:
        data = {"result" : "success"}
        r = json.dumps(data)
        return HttpResponse(r)
    else:
        data = {"result" : "Already registerd"}
        r = json.dumps(data)
        return HttpResponse(r)


def regauction2(request):
    lid=request.POST['lid']
    print(lid,"=====================")
    rr=registration.objects.get(login__id=lid)
    lid=rr.id
    data=[]
    with connection.cursor() as cursor:
        cursor.execute("SELECT `adminpart_product`.`pro_name`,`adminpart_product`.`price`,`adminpart_product`.`image`,`adminpart_product`.`date`,`adminpart_product`.`time`,`adminpart_product`.`id` FROM `adminpart_register_auction` JOIN `adminpart_product` WHERE `adminpart_register_auction`.pid_id=`adminpart_product`.id AND `adminpart_register_auction`.`user_id`="+str(lid)+"")
        ch = cursor.fetchall()
        data=[]
        for i in ch:
            row = {"pname": i[0], "price":i[1], "image":str(i[2]), "date":i[3], "time":i[4],"pid":i[5]}
            data.append(row)
        r=json.dumps(data)
        return HttpResponse (r)

def prod(request):
    id=request.POST['pid']
    print(id,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    i=product.objects.get(id=id)
    data=[]
    ob=bid.objects.filter(pid__id=id)
    if len(ob)==0:
        row = {"pname":i.pro_name, "price":i.price,"cprice":i.price,"id" : i.id}
        
        r=json.dumps(row)
        return HttpResponse (r)
    else:
        max=0
        for ii in ob:
            if float(ii.amt)>float(max):
                max=ii.amt
        row = {"pname":i.pro_name, "price":i.price,"cprice":ii.amt,"id" : i.id}

        
        r=json.dumps(row)
        return HttpResponse (r)
    
def view_auction_data(request):
    pid=request.POST['pid']
    lastmsgid=request.POST['lastmsgid']

    ob=bid.objects.filter(pid__id=pid,id__gte=int(lastmsgid)+1)

    
    result=[]
    for i in ob:
        
            print("hiiiiii")
           
            row={"date":str(i.adate)+"\n"+str(i.atime).split('.')[0],"message":i.amt,"fromid":i.user.login.id,"msgid":i.id,"name":str(i.user.firstname+" "+i.user.lastname)}
            result.append(row)
        
    if len(result)>0:
        try:
            obb=auction.objects.get(pid__id=pid)
            if(obb.time==0):
                print(obb.flag,type(obb.flag))
                if obb.flag=="1":
                    obb.time=30
                    obb.flag=2
                    obb.save()
                elif obb.flag=="2":
                    obb.flag=3
                    obb.time=30
                    obb.save()
                elif obb.flag=="3":
                    obb.flag=4
                    obb.save()
                

            else:
                if obb.time>0:
                    obb.time-=1
                    obb.save()
                    
            print(result,str(obb.flag),"*****************************")
            if str(obb.flag)=="4":
                print("completed")
                ob1=bid.objects.filter(pid__id=pid,status='sold')
                if len(ob1)>0:
                    r = json.dumps({"status":"ok","res1":result,"ts":"ok","flag":obb.flag,"time":obb.time,"s":"sold"})
                    return HttpResponse(r) 
                else:
                    ob1=bid.objects.filter(pid__id=pid,status='canceld')
                    
                    if len(ob1)>0:
                        r = json.dumps({"status":"ok","res1":result,"ts":"ok","flag":obb.flag,"time":obb.time,"s":"canceld"})
                        return HttpResponse(r) 
            r = json.dumps({"status":"ok","res1":result,"ts":"ok","flag":obb.flag,"time":obb.time,"s":"na"})
            print(r)
            return HttpResponse(r) 
        except:
            r = json.dumps({"status":"ok","res1":result,"ts":"na"})
            print(r)
            return HttpResponse(r) 

        
    else:

        try:
            obb=auction.objects.get(pid__id=pid)
            if(obb.time==0):
                print(obb.flag,type(obb.flag))
                if obb.flag=="1":
                    obb.time=30
                    obb.flag="2"
                    obb.save()
                elif obb.flag=="2":
                    obb.flag="3"
                    obb.time=30
                    obb.save()
                else:
                    obb.flag="4"
                    print("completed")
                    obb.save()
                    b=bid.objects.get(id=lastmsgid)
                    bmt=b.amt
                    ramt=b.pid.rprice

                    print(bmt,ramt,"aaaamount")

                    if int(bmt)>=int(ramt):
                        iob = bid.objects.get(id=lastmsgid)
                        if iob.status!='sold':
                            print("=========",iob.status)
                            print("=========",iob.status)
                            print("=========",iob.status)
                            print("=========",iob.status)
                            ob=product.objects.get(id=pid)
                            print("+++++++++++++",ob.status)
                            print("+++++++++++++",ob.status)
                            print("+++++++++++++",ob.status)
                            print("+++++++++++++",ob.status)
                            print("+++++++++++++",ob.status)
                            
                            send_mail('Bid 365', "Your product is sold"+ob.pro_name+"is sold for"+iob.amt+"to"+iob.user.firstname, 'alenchristy0201@gmail.com',[ob.user.email], fail_silently=False)
                        #
                        print("iffffeeeeeeeeeee")
                        
                        iob.status = "sold"
                        iob.save()
                        ob=product.objects.get(id=pid)
                        ob.status= "sold"
                        ob.save()
                        # print(ob.user.email,"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
                        # send_mail('Bid 365', "Your product is sold"+ob.pro_name+"is sold for"+iob.amt+"to"+iob.user.firstname, 'alenchristy0201@gmail.com',[ob.user.email], fail_silently=False)
                        # data = {"task" : "success"}
                        # r = json.dumps(data)
                        # return HttpResponse(r)
                    
                    else:
                        print("elseeeeeeeeee")
                        iob = bid.objects.get(id=lastmsgid)
                        iob.status = "canceld"
                        iob.save()
                        ob=product.objects.get(id=pid)
                        ob.status= "canceld"
                        ob.save()
                        # data = {"task" : "canceled"}
                        # r = json.dumps(data)
                        # return HttpResponse(r)
                        
            else:
                obb.time-=1
                obb.save()
            if obb.flag=="4":
                ob1=bid.objects.filter(pid__id=pid,status='sold')
                if len(ob1)>0:
                    r = json.dumps({"status":"ok","res1":result,"ts":"ok","flag":obb.flag,"time":obb.time,"s":"sold"})
                    return HttpResponse(r) 
                else:
                    ob1=bid.objects.filter(pid__id=pid,status='canceld')
                    
                    if len(ob1)>0:
                        r = json.dumps({"status":"ok","res1":result,"ts":"ok","flag":obb.flag,"time":obb.time,"s":"canceld"})
                        return HttpResponse(r) 
            r = json.dumps({"status":"na","ts":"ok","flag":obb.flag,"time":obb.time,"s":"na"})
            print(r)
            return HttpResponse(r) 
        except Exception as e:
            print(e,"+++++++++++++++++++++")
            r = json.dumps({"status":"na","ts":"na"})
            print(r)
            return HttpResponse(r) 
    
def insertbid(request):
        pid=request.POST['pid']
        lid=request.POST['lid']
        amt=request.POST['amt']
        date = str(datetime.datetime.now().date())
        time = str(datetime.datetime.now().time())

        ob=bid()
        ob.pid=product.objects.get(id=pid)
        ob.user=registration.objects.get(login__id=lid)
        ob.amt=amt
        ob.adate=date
        ob.atime=time
        ob.status='pending'

        ob.save()


        try:
            obb=auction.objects.get(pid__id=pid)
            obb.time=30
            obb.flag="1"
            obb.save()
        except:
            obb=auction()
            obb.pid=product.objects.get(id=pid)
            obb.time=30
            obb.flag="1"
            obb.save()

        data = {"task" : "success"}
        r = json.dumps(data)
        return HttpResponse(r) 

from django.core.mail import send_mail
def updatebid(request):
    id=request.POST['lastid']
    pid=request.POST['pid']
    b=bid.objects.get(id=id)
    bmt=b.amt
    ramt=b.pid.rprice
    print(bmt,ramt,"aaaamount")
    if int(bmt)>=int(ramt):
        print("iffffeeeeeeeeeee")
        iob = bid.objects.get(id=id)
        iob.status = "sold"
        iob.save()
        ob=product.objects.get(id=pid)
        ob.status= "sold"
        ob.save()
        message = f'''Your product is sold'''
        # [ob.u_id.email]
        name=ob.pro_name
        print(ob.user.email,"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
        send_mail('Bid 365', "Your product is sold"+ob.pro_name+"is sold for"+iob.amt+"to"+iob.user.firstname, 'alenchristy0201@gmail.com',[ob.user.email], fail_silently=False)
        data = {"task" : "success"}
        r = json.dumps(data)
        return HttpResponse(r)
    else:
        print("elseeeeeeeeee")
        iob = bid.objects.get(id=id)
        iob.status = "canceld"
        iob.save()
        ob=product.objects.get(id=pid)
        ob.status= "canceld"
        ob.save()
        send_mail('Bid 365', "Your product is unsold", 'alenchristy0201@gmail.com',['alenchristy0201@gmail.com'], fail_silently=False)
        data = {"task" : "canceled"}
        r = json.dumps(data)
        return HttpResponse(r)    
    
def dattim(request):
    pid=request.POST['pid']
    print(pid,"========================")
    data=[]
    # with connection.cursor() as cursor:
        # cursor.execute("SELECT `adminpart_product`.date, `adminpart_product`.time FROM `adminpart_product` WHERE id="+str(pid)+"")
        # ch = cursor.fetchone()
    ch=product.objects.filter(id=pid)
    print(ch,"********")
    for i in ch:
        row = {"date1":i.date, "time1":i.time}
        data.append(row)
    r=json.dumps(data)
    print(r,"============================")
    return HttpResponse (r)



def soldproduct(request):
    lid=request.POST['lid']
    print(lid,"=====================_____________________________________________________--")
    rr=registration.objects.get(login__id=lid)
    uid=rr.id
    print(uid,"#################################################")
    data=[]
    with connection.cursor() as cursor:
        cursor.execute("SELECT `adminpart_product`.`pro_name`,`adminpart_product`.`image`,`adminpart_product`.`price`,`adminpart_bid`.`amt`,`adminpart_bid`.`id` FROM `adminpart_product` JOIN `adminpart_bid` ON `adminpart_product`.`id`=`adminpart_bid`.`pid_id` WHERE `adminpart_product`.`user_id`="+str(uid)+" AND `adminpart_bid`.`status`='sold'")
        ch = cursor.fetchall()
        data=[]
        for i in ch:
            row = {"pname": i[0], "image":i[1], "sprice":str(i[2]), "bprice":i[3],"id":i[4]}
            data.append(row)
    r=json.dumps(data)
    print(r,"==========================================")
    return HttpResponse (r)

def wonproduct(request):
    lid=request.POST['lid']
    print(lid,"=====================_____________________________________________________--")
    rr=registration.objects.get(login__id=lid)
    uid=rr.id
    data=[]
    with connection.cursor() as cursor:
        cursor.execute("SELECT `adminpart_product`.`pro_name`,`adminpart_product`.`image`,`adminpart_product`.`price`,`adminpart_bid`.`amt`,`adminpart_bid`.`id`,`adminpart_rating`.`status` FROM `adminpart_product` JOIN `adminpart_bid` ON `adminpart_product`.`id`=`adminpart_bid`.`pid_id` LEFT JOIN `adminpart_rating` ON `adminpart_bid`.`user_id`=`adminpart_rating`.`by_id` WHERE `adminpart_bid`.`user_id`='"+str(uid)+"' AND `adminpart_bid`.`status`='sold' GROUP BY `adminpart_bid`.`id`")
        ch = cursor.fetchall()
        data=[]
        for i in ch:
            row = {"pname": i[0], "image":i[1], "sprice":str(i[2]), "bprice":i[3],"id":i[4],"status":i[5]}
            data.append(row)
    r=json.dumps(data)
    print(r,"==========================================")
    return HttpResponse (r)



def rejectbid(request):
    id=request.POST['pid']
    print(id,"**********************************************************")
    iob = bid.objects.get(id=id)
    iob.status = "rejected"
    iob.save()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r)


def name(request):
    id=request.POST['pid']
    i=product.objects.get(id=id)
    data=[]
    row = {"pname":i.pro_name}
    data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)

def payment(request):
    amt1=request.POST['amt']
    lid=request.POST['lid']
    
    ob=pay()
    ob.user=registration.objects.get(login__id=lid)
    ob.amt=amt1
    ob.save()
    data = {"task" : "success"}
    r = json.dumps(data)
    return HttpResponse(r) 




from django.http import HttpResponse
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import letter, A4
from reportlab.lib.units import inch
from reportlab.lib import colors
from reportlab.lib.utils import simpleSplit
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from datetime import date
from django.templatetags.static import static
from reportlab.lib.styles import getSampleStyleSheet



def contract(request,id):
    # Retrieve data from the database
    # print(request.GET)
    # pd = request.GET.get('id')
    print(id,"8888888888888888888888888888888888888888")
    ob=bid.objects.get(status='sold',id=id)
    by1=ob.user.firstname
    by2=ob.user.lastname
    eml=ob.user.email
    ph=ob.user.phone
    amt=ob.amt

    sell=ob.pid.user.firstname
    sel1=ob.pid.user.lastname
    ps=ob.pid.user.phone
    pn=ob.pid.pro_name
    
    # Create a new PDF document
    response = HttpResponse(content_type='application/pdf')
    response['Content-Disposition'] = 'attachment; filename="Auction_Contract.pdf"'
    pdf = canvas.Canvas(response, pagesize=A4)

    # Set the title for the PDF
    pdf.setTitle("My PDF Document")

    # Set the font for the heading
    pdf.setFont("Helvetica-Bold", 20)
    # Set the position for the heading and draw it
    pdf.drawCentredString(A4[0] / 2, A4[1] - inch, "Bid 365")

    font_name = "Times-Bold"
    pdf.setFont(font_name, 20)
    # Set the position for the heading and draw it
    x = A4[0] / 2
    y = A4[1] - inch - 35
    pdf.drawCentredString(x, y, "Auction Contract")

    # Draw an underline for the heading
    line_width = pdf.stringWidth("Auction Contract", "Helvetica-Bold", 20)
    pdf.line(x - line_width/2, y - 8, x + line_width/2, y - 8)


     # Define the border style
    border_width = 3
    border_color = colors.HexColor("#2c3e50")
    border_radius = 10

    pdf.setStrokeColorRGB(0.2, 0.4, 0.6)
    pdf.setLineWidth(3)
    pdf.roundRect(0.5 * inch, 0.5 * inch, A4[0] - inch, A4[1] - inch, 10, stroke=1, fill=0)
    


    today = date.today()
    cdate = today.strftime('%d/%m/%Y')

    pdf.setFont("Helvetica-Oblique", 13, leading=2)
    text = "This is a computer generated auction contract and is made and entered into as of {d} by and between {v3} and {v4} on the product {v1} of amount {v2} rupees.\n------------------------------------------------------------------------------------------------------\nTerms & Conditions \n1, Sale of Goods: The Seller agrees to sell to the Buyer, and the Buyer agrees to purchase from the Seller, the following product {v1}. \n 2, Dispute Resolution: Any disputes arising from the sale of the product or this Contract shall be resolved in accordance with the Auction Website's dispute resolution procedures. \n 3, Entire Agreement: This Contract contains the entire agreement between the Seller, the Buyer, and the Auction Website and supersedes all prior agreements and understandings, whether written or oral, relating to the sale of the product.".format(v1=pn, v2=amt,v3=sell,v4=by1,d=cdate)
    
    text_width = A4[0] - inch - 2 * 0.3 * inch
    text_height = A4[1] - inch - 2 * 0.4 * inch

    x = 1 * inch
    y = 1 * inch + (A4[1] - inch - text_height) / 2 
    
    # Split text into lines based on available width and font size
    lines = simpleSplit(text, pdf._fontname, pdf._fontsize, text_width)

    # Calculate the height of the lines and the space between them
    line_height = pdf._leading * pdf._fontsize
    space_between_lines = line_height / 20

    # Calculate the total height of the text
    total_height = len(lines) * line_height + (len(lines) - 1) * space_between_lines

    # Calculate the starting y coordinate for the text
    text_y = y + text_height - (text_height - total_height) / 2 - line_height

    # Draw lines inside the border
    for line in lines:
        pdf.drawString(x, text_y, line.strip())
        text_y -= line_height + space_between_lines



    pdf.setFont("Helvetica", 12, leading=1)
    text2 = "Seller \n {v3} {sel1} \n {e} \n{ph}".format(v1=pn, v2=amt,v3=sell,v4=by1,d=cdate,e=eml,ph=ph,sel1=sel1)

    text_width2 = A4[0] - inch - 2 * 0.3 * inch
    text_height = A4[1] - inch - 2 * 0.4 * inch

    x2 = 1.8 * inch
    y2 = 0.1 * inch

    # Split text into lines based on available width and font size
    lines = simpleSplit(text2, pdf._fontname, pdf._fontsize, text_width2)

    # Calculate the height of the lines and the space between them
    line_height = pdf._leading * pdf._fontsize
    space_between_lines = line_height / 20

    # Calculate the total height of the text
    total_height2 = len(lines) * line_height + (len(lines) - 1) * space_between_lines

    # Calculate the starting y coordinate for the text
    text_y1 = inch + 0.9 * inch

    # Draw lines inside the border
    for line in lines:
        pdf.drawCentredString(x2, text_y1, line.strip())
        text_y1 -= line_height + space_between_lines



    pdf.setFont("Helvetica", 12, leading=1)
    text2 = "Buyer \n {v4} {by2} \n {e} \n{p}".format(v1=pn, v2=amt,v3=sell,v4=by1,d=cdate,e=eml,p=ps,by2=by2)

    text_width2 = A4[0] - inch - 2 * 0.3 * inch
    text_height = A4[1] - inch - 2 * 0.4 * inch
    seller_width = pdf.stringWidth("Seller", fontName="Helvetica", fontSize=12)
    x2 = 6.5 * inch
    y2 = 0.1 * inch

    # Split text into lines based on available width and font size
    lines = simpleSplit(text2, pdf._fontname, pdf._fontsize, text_width2)

    # Calculate the height of the lines and the space between them
    line_height = pdf._leading * pdf._fontsize
    space_between_lines = line_height / 20

    # Calculate the total height of the text
    total_height2 = len(lines) * line_height + (len(lines) - 1) * space_between_lines

    # Calculate the starting y coordinate for the text
    text_y1 = inch + 0.9 * inch

    # Draw lines inside the border
    for line in lines:
        pdf.drawCentredString(x2, text_y1, line.strip())
        text_y1 -= line_height + space_between_lines

      
    # Save the PDF
    pdf.save()
    return response



def ratng(request):
    bd = request.POST['bd']
    print(bd,"###############################")
    ob=bid.objects.get(id=bd)
    by=ob.user.id
    sl=ob.pid.user
    rv=request.POST['review']
    rt=request.POST['rating']


    bc=rating()
    bc.sl=sl
    bc.by=registration.objects.get(id=by)
    bc.review=rv
    bc.rate=rt
    bc.status="rated"
    bc.save()
    data = {"task":"success"}
    r = json.dumps(data)
    return HttpResponse(r)


def allrating(request):
    pd=request.POST['pid']
    print(pd,"=====================_____________________________________________________--")
    rr=product.objects.get(id=pd)
    print(rr,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
    uid=rr.user.id
    print(uid,"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
    with connection.cursor() as cursor:
        cursor.execute("SELECT `adminpart_rating`.review,`adminpart_rating`.rate,`adminpart_registration`.`firstname` FROM `adminpart_rating` JOIN `adminpart_registration` ON `adminpart_registration`.`id`=`adminpart_rating`.`by_id` WHERE `adminpart_rating`.`sl_id`="+str(uid)+"")
        ch = cursor.fetchall()
        data=[]
        for i in ch:
            row = {"review": i[0], "rating":str(i[1]),"name":str(i[2])}
            data.append(row)
    r=json.dumps(data)
    print(r,"==========================================")
    return HttpResponse (r)


def feedb(request):
    ld = request.POST['lid']
    rr=registration.objects.get(login__id=ld)
    uid=rr.id
    feed=request.POST['feed']
    from datetime import date
    dat = date.today()

    bc=feedback()
    bc.user=registration.objects.get(id=uid)
    bc.feedb=feed
    bc.date=dat
    bc.save()
    data = {"task":"success"}
    r = json.dumps(data)
    return HttpResponse(r)

def paytm(request):
    ld = request.POST['lid']
    pnm= request.POST['pname']
    rr=registration.objects.get(login__id=ld)
    uid=rr.id
    amt=request.POST['amt']
    from datetime import date
    dat = date.today()

    bc=pay()
    bc.user=registration.objects.get(id=uid)
    bc.amt=amt
    bc.pname=pnm
    bc.date=dat
    bc.save()
    data = {"task":"success"}
    r = json.dumps(data)
    return HttpResponse(r)

# def viewname(request):
#     ld = request.POST['lid']
#     rr=registration.objects.get(login__id=ld)
#     uid=rr.firstname












        







    
























# Create your views here.
