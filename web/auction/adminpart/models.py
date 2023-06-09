from django.db import models

# Create your models here.

class login(models.Model):
    username = models.CharField(unique= True, max_length=20)
    password = models.CharField(max_length=20)
    usertype = models.CharField(max_length=10)

class registration(models.Model):
    login = models.ForeignKey(login,on_delete=models.CASCADE)
    lastname = models.CharField(max_length=20)
    firstname = models.CharField(max_length=20)
    address = models.CharField(max_length=100)
    gender = models.CharField(max_length=10)
    phone = models.CharField(max_length=15)
    email = models.EmailField(max_length=30)

class category(models.Model):
    category_nam = models.CharField(unique=True, max_length=20)
    image = models.ImageField()

class product(models.Model):
    catg = models.ForeignKey(category,on_delete=models.CASCADE)
    user = models.ForeignKey(registration,on_delete=models.CASCADE)
    pro_name = models.CharField(max_length=25)
    details = models.CharField(max_length=300)
    price = models.CharField(max_length=10)
    rprice = models.CharField(max_length=10)
    image = models.ImageField(max_length=100)
    place = models.CharField(max_length=30)
    date = models.CharField(max_length=20)
    time = models.CharField(max_length=20)
    status = models.CharField(max_length=25)


class wishlist(models.Model):
    user = models.ForeignKey(registration,on_delete=models.CASCADE)
    product = models.ForeignKey(product,on_delete=models.CASCADE)


class notif(models.Model):
    product = models.ForeignKey(product,on_delete=models.CASCADE)
    notification = models.CharField(max_length=100)


class chat(models.Model):
    fid = models.ForeignKey(registration,on_delete=models.CASCADE,related_name='fid')
    tid = models.ForeignKey(registration,on_delete=models.CASCADE,related_name='tid')
    product = models.ForeignKey(product,on_delete=models.CASCADE)
    doubt = models.CharField(max_length=300)
    reply = models.CharField(max_length=300)
    cdate = models.DateField()
    ctime = models.TimeField()

class register_auction(models.Model):
    pid = models.ForeignKey(product,on_delete=models.CASCADE)
    user = models.ForeignKey(registration,on_delete=models.CASCADE)
    date = models.DateField()
    status = models.CharField(max_length=15)

class notf(models.Model):
    pid = models.ForeignKey(product,on_delete=models.CASCADE)
    user = models.ForeignKey(registration,on_delete=models.CASCADE)

class bid(models.Model):
    pid = models.ForeignKey(product,on_delete=models.CASCADE)
    user = models.ForeignKey(registration,on_delete=models.CASCADE)
    amt = models.CharField(max_length=20)
    adate = models.DateField()
    atime = models.TimeField()
    status = models.CharField(max_length=20)


class auction(models.Model):
    pid = models.ForeignKey(product,on_delete=models.CASCADE)
    time = models.IntegerField()
    flag = models.CharField(max_length=15)

class pay(models.Model):
    user = models.ForeignKey(registration,on_delete=models.CASCADE)
    amt = models.IntegerField()
    pname= models.CharField(max_length=20)
    date = models.DateField()

class rating(models.Model):
    sl = models.ForeignKey(registration,on_delete=models.CASCADE,related_name='sl')
    by = models.ForeignKey(registration,on_delete=models.CASCADE,related_name='by')
    review = models.CharField(max_length=200)
    rate = models.FloatField()
    status= models.CharField(max_length=20)

class feedback(models.Model):
    user = models.ForeignKey(registration,on_delete=models.CASCADE)
    feedb = models.CharField(max_length=200)
    date = models.DateField()


    
    




    
