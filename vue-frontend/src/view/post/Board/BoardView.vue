<template>
    <div class="board-router-view">
        <div v-if="this.$route.params.boardname === 'freeBoard'">
            <BoardListForm></BoardListForm>
        </div>
        <div v-if="this.$route.params.boardname === 'OOTD'">
            <OotdForm></OotdForm>
        </div>
        <div v-if="this.$route.params.boardname === 'qna'">
            <QnaListForm></QnaListForm>
        </div>
    </div>
</template>

<script>
import BoardListForm from "@/components/post/Board/BoardListForm";
import CardForm from "@/components/post/OOTD/OotdForm";
import OotdForm from "@/components/post/OOTD/OotdForm";
import QnaListForm from "@/components/post/Qna/QnaListForm";

export default {
    data() {
        return {}
    },
    components: {OotdForm, QnaListForm, BoardListForm, CardForm},
    methods   : {},
    created() {
    }
}
</script>

<style scoped>

</style>