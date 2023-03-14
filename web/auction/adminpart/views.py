from django.http import HttpResponse
from django.shortcuts import render
from adminpart.models import *
from django.core.files.storage import FileSystemStorage

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
    print(ob,"################################")
    ob.delete()
    # request.session['pid'] = id
    return HttpResponse('''<script> alert ("removed"); window.location='/viewpro'</script>)''')


# def removeproduct(request):
#    reason = request.POST['radio']
# #    ob = notif()
# #    ob.product = product.objects.get(id=request.session['pid'])
   
#    ob1=product.objects.get(id=request.session['pid'])
#    ob1.delete()
#    data = {"task" : "success"}
#    r = json.dumps(data)
#    return HttpResponse(r)


    


    
     







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
    ob1=product.objects.exclude(user__login__id=lid)
    data1=[]
    for i in ob1:
            status="0"
            obb=wishlist.objects.filter(user__login__id=lid,product__id=i.id)
            if len(obb)>0:
                status="1"
            row = {"id": i.id, "pname": i.pro_name,"img":i.image.url,"price":i.price,"status":status}
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
    cursor.execute("SELECT* FROM `adminpart_product` WHERE `pro_name` LIKE '%"+str(txt)+"%'")
    res=cursor.fetchall()
    print(res,"==========================")
    # cursor.execute("SELECT* FROM `adminpart_product` WHERE `pro_name` LIKE '%"+str(txt)+"%'")
    # res=cursor.fetchall()
    data = []
    for a in res:
        row = {"pname":a[1],"price": a[3], "img":"/media/"+a[4],"id":a[0]}
        data.append(row)
    r = json.dumps(data)
    return HttpResponse(r)
    


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


def productview(request):
    id=request.POST['pid']
    i=product.objects.get(id=id)
    data=[]
    row = {"pname":i.pro_name, "details":i.details, "image":str(i.image), "date":i.date, "price":i.price,"time":i.time, "place":i.place, "id" : i.id}
    data.append(row)
    r=json.dumps(data)
    return HttpResponse (r)


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
            row = {"id": i.id, "pname": i.pro_name,"img":i.image.url,"price":i.price,"status":status}
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












# Create your views here.
