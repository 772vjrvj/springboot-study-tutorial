<template>
  <validation-provider
      name="Name"
      :rules="rules"
      v-slot="validationContext"
  >
    <b-form-group id="example-input-group-1" :label="text" :label-for="text">
      <b-form-datepicker
          type=""
          :id="text"
          :name="text"
          v-model="value"
          :state="getValidationState(validationContext)"
          :aria-describedby="`${text}-describedby`"
          @input="handleInput"
      ></b-form-datepicker>
      <b-form-invalid-feedback :id="`${text}-describedby`">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
    </b-form-group>
  </validation-provider>
</template>

<script>

export default {
  name: 'FormValidInput',
  props: {
    parentKey: String,
    text: String,
    rules: Object,
    type: String,
  },
  data() {
    return {
      value: '',
    }
  },
  methods: {
    getValidationState({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null;
    },
    handleInput(value){
      this.$emit('formInputChange',value, this.parentKey);
    }
  },
}
</script>
<style scoped>

</style>