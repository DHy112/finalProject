package com.bit.ww.controller;

import com.bit.ww.dto.BoardDTO;
import com.bit.ww.dto.CmntDTO;
import com.bit.ww.dto.PostDTO;
import com.bit.ww.service.BoardService;
import com.bit.ww.service.CmntService;
import com.bit.ww.service.LikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cmnty")
public class BoardReadController {
    private final BoardService boardService;
    private final CmntService cmntService;
    private final LikeService likeService;

    // 게시판
    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보 조회")
    @GetMapping("/{boardname}")
    public BoardDTO boardInfo(@PathVariable String boardname) {
        return boardService.findBoard(boardname);
    }

    // 페이징 완료
    @ApiOperation(value = "게시판 글 리스트 - 페이징", notes = "게시판 글 리스트 - 페이징")
    @GetMapping("/{boardname}/posts")
    public HashMap posts(@PathVariable String boardname, @RequestParam(value = "page", defaultValue = "1") int pagenum) {
        HashMap<String, Object> posts = new HashMap<>();
        posts.put("posts", boardService.findPosts(boardname, pagenum));
        posts.put("cntPosts", boardService.cntPosts(boardname));
        posts.put("pageList", boardService.findPageList(boardname, pagenum));
        return posts;
    }

    // 게시물 상세보기
    @ApiOperation(value = "게시물 상세보기", notes = "게시물 상세보기")
    @GetMapping("/post/{postId}")
    public HashMap post(@PathVariable("postId") int num, String uid) {
        HashMap<String, Object> post = new HashMap<>();
        PostDTO postDTO = boardService.getPost(num);
        int readcount = postDTO.getReadcount() + 1;
        postDTO.setReadcount(readcount);
        boardService.savePost(postDTO);
        // 포스트
        post.put("post", postDTO);
        int boardnum = postDTO.getBoardnum();
        // 좋아요
        post.put("existLike", likeService.existLike(boardnum, num, uid));
        // 댓글, 대댓글
        List<CmntDTO> cmntList = cmntService.getCmntList(boardnum, num, 0);
        List<List<CmntDTO>> cmnt2List = new ArrayList<>();
        for (int i = 0; i < cmntList.size(); i++) {
            List<CmntDTO> temp = cmntService.getCmnt2List(boardnum, num, cmntList.get(i).getNum(), 1);
            cmnt2List.add(temp);
        }
        post.put("cmntList", cmntList);
        post.put("cmnt2List", cmnt2List);
        return post;
    }

    // 검색
    @ApiOperation(value = "게시판 검색 리스트 - 제목", notes = "게시판 글 리스트 - 제목")
    @GetMapping("/{boardname}/searchTitle/{search}")
    public HashMap searchTitle(@PathVariable String boardname, @PathVariable String search) {
        HashMap<String, Object> posts = new HashMap<>();
        posts.put("posts", boardService.searchTitle(search, boardname));
        posts.put("cntPosts", boardService.cntSearchTitle(search, boardname));
        return posts;
    }

    // 각 게시판별 내글보기
    @ApiOperation(value = "내글보기", notes = "내글보기")
    @GetMapping("/{boardname}/searchUid/{uid}")
    public HashMap searchUid(@PathVariable String boardname, @PathVariable String uid) {
        HashMap<String, Object> posts = new HashMap<>();
        posts.put("posts", boardService.searchUid(boardname, uid));
        posts.put("cntPosts", boardService.cntSearchUid(boardname, uid));
        return posts;
    }

    @ApiOperation(value = "내글보기 - 검색 리스트 - 제목", notes = "내글보기 - 검색 리스트 - 제목")
    @GetMapping("/{boardname}/searchUidAndTitle/{uid}/{search}")
    public HashMap searchUidAndTitle(@PathVariable String boardname, @PathVariable String uid, @PathVariable String search) {
        HashMap<String, Object> posts = new HashMap<>();
        posts.put("posts", boardService.searchUidAndTitle(boardname, uid, search));
        posts.put("cntPosts", boardService.cntSearchUidAndTitle(boardname, uid, search));
        return posts;
    }

    @ApiOperation(value = "내글보기 - 검색 리스트 - 제목+내용", notes = "내글보기 - 검색 리스트 - 제목+내용")
    @GetMapping("/{boardname}/searchUidAndTitleOrContent/{uid}/{search}")
    public HashMap searchUidAndTitleOrContent(@PathVariable String boardname, @PathVariable String uid, @PathVariable String search) {
        HashMap<String, Object> posts = new HashMap<>();
        posts.put("posts", boardService.searchUidAndTitleOrContent(boardname, uid, search));
        posts.put("cntPosts", boardService.cntSearchUidAndTitleOrContent(boardname, uid, search));
        return posts;
    }

    // 메인 - 인기글 - 조회수
    @ApiOperation(value = "인기글 - 조회수", notes = "인기글 - 조회수")
    @GetMapping("/main/{boardname}")
    public List findReadcountPosts(@PathVariable String boardname) {
        return boardService.findReadcountPosts(boardname);
    }

    @ApiOperation(value = "인기글 - 좋아요", notes = "인기글 - 좋아요")
    @GetMapping("/main/{test}")
    public List findLikecountPosts(@PathVariable String boardname) {
        return boardService.findLikecountPosts(boardname);
    }

    @ApiOperation(value = "게시판 검색 리스트 - 제목+내용", notes = "게시판 글 리스트 - 제목+내용")
    @GetMapping("/{boardname}/searchTitleOrContent/{search}")
    public HashMap searchTitleOrContent(@PathVariable String boardname, @PathVariable String search) {
        HashMap<String, Object> posts = new HashMap<>();
        posts.put("posts", boardService.searchTitleOrContent(search, boardname));
        posts.put("cntPosts", boardService.cntSearchTitleOrContent(search, boardname));
        return posts;
    }
}