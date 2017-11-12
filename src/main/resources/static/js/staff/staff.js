import Multiselect from 'vue-multiselect';

Vue.component('multiselect', Multiselect);

export default {
	components: {Multiselect},
	data() {
		return {
			value: null,
			options: ['list', 'of', 'options']
		}
	}
}
