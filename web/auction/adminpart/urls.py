
from django.contrib import admin
from django.urls import path
from adminpart import views
urlpatterns = [
    path('',views.login1,name='login1'),
    path('logincode',views.logincode,name='logincode'),
    path('hom',views.hom,name='hom'),
    path('hom1',views.hom1,name='hom1'),
    path('cat',views.cat,name='cat'),
    path('adcat',views.adcat,name='adcat'),
    path('adnewcat',views.adnewcat,name='adnewcat'),
    path('contract/<int:id>',views.contract,name='contract'),

    
    
    
    # path('viewcat',views.viewcat,name='viewcat'),
    path('upcat/<int:id>',views.upcat,name='upcat'),
    path('android_login',views.android_login,name='android_login'),
    path('viewuser',views.viewuser,name='viewuser'),

    path('upcat/updatecatg',views.updatecatg,name='updatecatg'),

    path('reg',views.reg,name='reg'),

    path('viewpro',views.viewpro,name='viewpro'),

    path('removepro<int:id>',views.removepro,name='removepro'),

    path('removeproduct',views.removeproduct,name='removeproduct'),

    path('chats',views.chats,name='chats'),

    path('view_message2',views.view_message2,name='view_message2'),

    path('viewchats',views.viewchats,name='viewchats'),

    path('regauction',views.regauction,name='regauction'),

    path('regauction1',views.regauction1,name='regauction1'),

    path('checkservice',views.checkservice,name='checkservice'),

    path('regauction2',views.regauction2,name='regauction2'),

    path('prod',views.prod,name='prod'),

    path('insertbid',views.insertbid,name='insertbid'),

    path('view_auction_data',views.view_auction_data,name='view_auction_data'),

    path('dattim',views.dattim,name='dattim'),

    path('soldproduct',views.soldproduct,name='soldproduct'),

    path('updatebid',views.updatebid,name='updatebid'),

    path('rejectbid',views.rejectbid,name='rejectbid'),

    path('name',views.name,name='name'),

    path('feed',views.feed,name='feed'),

    path('auction2',views.auction2,name='auction2'),

    path('auction1',views.auction1,name='auction1'),

    path('earnings',views.earnings,name='earnings'),

    path('paytm',views.paytm,name='paytm'),

    












    








   
    path(' android_login',views. android_login,name=' android_login'),

    path('view_profile',views.view_profile,name='view_profile'),

    path('updateprofile',views.updateprofile,name='updateprofile'),

    path('viewcatg',views.viewcatg,name='viewcatg'),

    path('adproduct',views.adproduct,name='adproduct'),

    path('viewuserprodct',views.viewuserprodct,name='viewuserprodct'),

    path('deleteproduct',views.deleteproduct,name='deleteproduct'),

    path('updateview',views.updateview,name='updateview'),

    path('updateproduct',views.updateproduct,name='updateproduct'),

    path('updateproduct2',views.updateproduct2,name='updateproduct2'),

    path('select_home',views.select_home,name='select_home'),

    path('viewcatprodct',views.viewcatprodct,name='viewcatprodct'),
    path('search_view',views.search_view,name='search_view'),
    path('insert_wish',views.insert_wish,name='insert_wish'),
    path('delete_wish',views.delete_wish,name='delete_wish'),

    path('productview',views.productview,name='productview'),

    path('viewcatprodct1',views.viewcatprodct1,name='viewcatprodct1'),

    path('viewprodct2',views.viewprodct2,name='viewprodct2'),

    path('payment',views.payment,name='payment'),

    path('wonproduct',views.wonproduct,name='wonproduct'),
    path('ratng',views.ratng,name='ratng'),

    path('allrating',views.allrating,name='allrating'),

    path('feedb',views.feedb,name='feedb'),
    
    
    # path('rateview',views.rateview,name='rateview'),

    


   
]
