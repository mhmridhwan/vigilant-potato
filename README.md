# vigilant-potato
Checklist module api:
1. create user (ok);
2. create booking
	1. user hanya bisa memilih 3 locker (ok)
	2. pendaftaran berdasarkan hp, ktp, email (ok)
	3. deposit perlocker-perhari (ok)
	4. refund Ketika pengembalian (ok)
	5. jika user yang meminjam telat mengembalikan lebih dari sehari, maka dikenakan denda sebesar 5000 per-hari (ok)
	6. jika booking selesai, maka akan dikirim email untuk no locker & password (ok)
3. access locker hanya untuk simpan dan pengambilan untuk booking tsb
4. pinjam baru = generate pass baru; (ok)
5. Waktu pengembalian, maka deposit di kembalikan; (ok)
6. jika ada denda, maka dipotong dr deposit; (ok)
7. jika denda lebih dari deposit, maka bayar langsung ditempat (tidak disistem); (ok)
8. locker hanya bisa dibuka oleh pass yg diemail; (ok)
9. 3x salah password, maka ada denda 25000 untuk release locker dan deposit tidak di refund; (ok)

