/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws.a.PraUjian_PWS;

import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.a.PraUjian_PWS.exceptions.PreexistingEntityException;

/**
 *
 * @author akmal
 */
@Controller
@ResponseBody
public class BarangController {

    Barang data = new Barang();
    BarangJpaController BarangJpa = new BarangJpaController();

    //menampilkan data by id
    @RequestMapping("/getName/{id}")
    public String getName(@PathVariable("id") int id) {
        try {
            data = BarangJpa.findBarang(id);
            return data.getName() + "<br>" + data.getJumlah();
        } catch (Exception e) {
            return "Data Not Found";
        }
    }

    //mapping untuk createBarang
    @PostMapping("/createBarang")
    //method untuk create Barang
    public String createBarang(@RequestBody Barang barang) {
        try {
            BarangJpa.create(barang);
            //return ketikan barang berhasil di buat
            return "Barang telah ditambahakan ";
        } catch (Exception e) {
            //return ketika mnemabahkan data dgn id yang telah dipakai
            return " id telah dipakai";
        }
    }
    
    //mapping untuk updateBarang
    @PutMapping("/updateBarang/{id}")
    //method untuk updateBarang
    public String updateBarang(@PathVariable("id") int id, @RequestBody Barang barang) {
        try {
            //deklarasi data untuk mencari barang berdasarkan id
            data = BarangJpa.findBarang(id);

            //untuk ngset id berdasarkan id yang telah diambil
            barang.setId(id);

            //deklarasi Request Body Barang
            Barang brg = new Barang();

            //deklarasa brg untuk mencari barang berdasarkan id
            brg = BarangJpa.findBarang(id);

            //kondisi ketika update barang yang tidak ada
            if (barang.getId() != id) {
                return " Barang tidak ditemukan";
            } //kondisi ketika mengupdate hanya untuk nama barang
            else if (barang.getName() == null) {
                barang.setName(data.getName());
                BarangJpa.edit(barang);
                brg = BarangJpa.findBarang(id);
                return "Data telah diupdate!!!  "
                        + " \nid :  " + brg.getId()
                        + "\nname : " + brg.getName()
                        + "\njumlah : " + brg.getJumlah();
            } //kondisi ketika mengupdate hanya untuk jumlah barang
            else if (barang.getJumlah() == null) {
                barang.setJumlah(data.getJumlah());
                BarangJpa.edit(barang);
                brg = BarangJpa.findBarang(id);
                return "Data telah diupdate!!!  "
                        + " \nid :  " + brg.getId()
                        + "\nname : " + brg.getName()
                        + "\njumlah : " + brg.getJumlah();
            } //kondisi ketika harus update nama dan jumlah 
            else if (barang.getName() != null && barang.getJumlah() != null) {
                BarangJpa.edit(barang);
                brg = BarangJpa.findBarang(id);
                return "Data telah diupdate!!!  "
                        + " \nid :  " + brg.getId()
                        + "\nname : " + brg.getName()
                        + "\njumlah : " + brg.getJumlah();
            } else {
                return "Data tidak ditemukan";
            }
        } catch (Exception e) {
            return "Data tidak ada";
        }
    }

    //menampilkan semua data
    @RequestMapping("/getAll")
    public List<Barang> viewAll() {
        return BarangJpa.findBarangEntities();
    }

//menghapus semua data by id
    @RequestMapping("/deleteBarang/{id}")
    public String deleteName(@PathVariable("id") int id) {
        try {
            BarangJpa.destroy(id);
            return "Data" + id + "has been deteled";

        } catch (Exception e) {
            return id + "Data Not Found";
        }
    }
}
