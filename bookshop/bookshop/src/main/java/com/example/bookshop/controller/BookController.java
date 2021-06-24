package com.example.bookshop.controller;

import com.example.bookshop.bean.RespBean;
import com.example.bookshop.entity.Book;
import com.example.bookshop.mapper.BookMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("book")
@Api(tags = "BookController",value = "书本控制器")
public class BookController {

    @Autowired
    BookMapper bookMapper ;


    @GetMapping("/index")
    @ApiOperation("查询所有书本")
    public RespBean index(){
        List<Book> list = bookMapper.list();
        setImg(list);
        return RespBean.success("成功",list);
    }

    @PostMapping("mybook")
    @ApiOperation("获取当前用户所拥有的书本，参数为用户ID")
    public RespBean myBook(@ApiParam(name = "id", value = "1", required = true) Integer id ){
        List<Book> books = bookMapper.listBookByUser(id);
            setImg(books);
        return RespBean.success("成功",books);
    }

    @PostMapping("detail/{id}")
    @ApiOperation("获取当前书本的详细内容，参数为BookID")
    public RespBean detail(@ApiParam(name = "id", value = "1", required = true)  @PathVariable("id") Integer id  ){
        Book byId = bookMapper.findById(id);
        return RespBean.success("成功",byId);
    }

    /**
     * 设置图片编码 base64
     * @param list
     */
    private void setImg(List<Book>list ){
        for (int i = 0; i <list.size() ; i++) {
            String img = img(list.get(i).getBookImage());
            list.get(i).setBookImage(img);
        }
    }

    private String img(String path){
            String imageString = "";
            try {
                FileInputStream fis = new FileInputStream(path);
                int count = 0;
                while (count == 0) {
                    count = fis.available();
                }
                byte[] read = new byte[count];
                System.err.println(read);
                fis.read(read);
                imageString = Base64.getEncoder().encodeToString(read);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  imageString;
    }


}
