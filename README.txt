1) À la racine du projet: faire un mvn clean install
2) Récupérer l'archive Zip dans le target, puis la décompresser
3) Il vous faut un fichier dans lequel vous mettrez les packages en entrée, et un fichier en sortie dans lequel seront écrites les optimisations (par le batch lui-même)
L'emplacement de ces fichiers est défini dans l'archive décompréssé [batch/voyage/conf/tech.properties]
4) Ensuite, aller dans l'archive décompréssée [batch/voyage]
5) Ouvrir un git bash ou une commande windows à cet emplacement de l'archive décompréssé [batch/voyage]
6) Puis taper "sh voyage.sh"