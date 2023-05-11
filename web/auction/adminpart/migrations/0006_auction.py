# Generated by Django 4.1.7 on 2023-04-04 09:45

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('adminpart', '0005_bid'),
    ]

    operations = [
        migrations.CreateModel(
            name='auction',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('bids', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='adminpart.bid')),
            ],
        ),
    ]
