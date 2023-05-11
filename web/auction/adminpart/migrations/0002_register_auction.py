# Generated by Django 4.1.7 on 2023-03-24 06:44

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('adminpart', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='register_auction',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('date', models.DateField()),
                ('pid', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='adminpart.product')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='adminpart.registration')),
            ],
        ),
    ]