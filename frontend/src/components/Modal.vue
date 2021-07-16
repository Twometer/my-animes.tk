<template>
    <transition name="fade">
        <div class="dialog" v-if="open">
            <div
                class="dialog-content"
                :style="{ padding: borderless ? '0' : '2rem' }"
            >
                <slot></slot>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
    name: 'Modal',
    props: {
        open: Boolean,
        borderless: Boolean,
    },
    watch: {
        open: function (newOpen) {
            if (newOpen) document.body.classList.add('no-scrollbar');
            else document.body.classList.remove('no-scrollbar');
        },
    },
    unmounted() {
        document.body.classList.remove('no-scrollbar');
    },
};
</script>

<style scoped>
.dialog {
    position: fixed;
    backdrop-filter: blur(8px);
    background-color: rgba(0, 0, 0, 0.45);
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    overflow-x: hidden;
    overflow-y: auto;
    z-index: 1100;
}
.dialog-content {
    background-color: white;
    width: 40%;
    margin: auto;
    margin-top: 5rem;
    margin-bottom: 5rem;
    border-radius: 5px;
    box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.3);
}

.fade-enter-active,
.fade-leave-active {
    transition-duration: 0.3s;
}
.fade-enter-from,
.fade-leave-to {
    padding-top: 3%;
    opacity: 0;
}
</style>
