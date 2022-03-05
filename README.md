# IF3210-2022-Android-59

<br />

<!-- ABOUT THE PROJECT -->
## About the Application
Aplikasi Perlu Dilindungi merupakan aplikasi pelacak Covid-19. Aplikasi dibuat oleh mahasiswa informatika 2019 ITB. Aplikasi ini dapat menampilkan berita - berita mengenai covid-19 yang ada di Indonesia. Selain itu, aplikasi ini dapat mencari fasilitas kesehatan yang berada disekitar pengguna sehingga pengguna mengetahui fasilitas yang tersedia didekatnya jika ingin melakukan vaksinasi. Selain itu, aplikasi ini dapat menambahkan fasilitas kesehataan sebagai fasilitas yang paling favorit. Fitur lainnya adalah aplikasi dapat memindai suhu Anda dan juga melakukan check in jika Anda ingin masuk ke suatu tempat.


### How This Application Works
<!-- Cara kerja, terutama mengenai pemenuhan spesifikasi aplikasi. -->
Aplikasi dibuat menggunakan android studio. Beberapa fitur diimplementasikan menggunakan activity dan fragment
Terdapat 3 activity, yaitu :
* MainActivity : sebagai activity utama ketika aplikasi dijalankan
* CaptureActivityPortrait : sebagai activity untuk menscan QR Code 
* CheckInActivity     : sebagai activity untuk check in user 

Selain menggunakan activity, beberap fitur menggunakan fragment, fitur tersebut berdiri diatas MainActivity.
Fitur tersebut adalah :
* NewsFragment : Fragment ini berfungsi untuk menampilkan list berita mengenai covid-19
* VaksinLocationFragment : Fragment ini berfungsi untuk menampilkan fasilitas kesehataan
* VaksinLocationDetailFragment : Fragment ini berfungsi untuk menampilkan detail dari suatu fasilitas kesehatan
* BookmarkFragment : Fragment ini berfungsi untuk menampilkan bookmark fasilitas kesehataan yang pengguna pilih
* NewsDetailFragment : Fragment ini berfungsi untuk menampilkan suatu berita menggunakan webviews

beberapa fitur seperti list news, list fasilitas kesehatan, dan bookmark list menggunakan Recircle View



### Library Used
* Room : Digunakan untuk SQLLite pada mobile
* Coroutines : Menjalankan query database di background / secara async
* Glide : Library Images pada News Page
* Location : Library untuk mengetahui Location dari user
* Zxing : Library untuk scan QRCode
* Retrofit : Library digunakan untuk menjalankan HTTP request
* OkHTTP : Menampilkan log dari respond request sehingga dapat melihat apakah request berhasil atau tidak

## Screenshot

## Work Distribution
| NIM | Nama | Kontribusi |
| ------ | ------ | ------ |
| 13519197| Muhammad Jafar Gundari | All news feature, setup database, setup Retrofit, Operasi database bookmark di detail, scan QR Code, Temperature (CheckIn), Merapihkan Layout  |
| 13519178| Akeyla Pradia Naufal| Setup check in |
| 13519168| Nabil Nabighah | All fasilitas kesehataan + detail fasilitas kesehatan + bookmark feature + Merapihkan Layout|







